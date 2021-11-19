package com.example.cinebuzz.dashboard

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomMovie : AppCompatActivity() {

    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    lateinit var nextBtn: Button
    lateinit var category: String
    lateinit var cardView: CardView
    lateinit var ratingBar: RatingBar

    //    lateinit var progressBar: ProgressBar
    private lateinit var Shimmer: ShimmerFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_movie)

        supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                        ContextCompat.getColor(
                                this,
                                android.R.color.black
                        )
                )
        )
        movieImage = findViewById(R.id.movie_image)
        movieName = findViewById(R.id.movie_names)
        nextBtn = findViewById(R.id.button)
        Shimmer = findViewById(R.id.randomshimmer)
        cardView = findViewById(R.id.cardView)
        ratingBar = findViewById(R.id.ratingBar)
//        progressBar = findViewById(R.id.progressBar)
        category = intent.getStringExtra("Category")!!

        findMovie()

        nextBtn.setOnClickListener(View.OnClickListener {
            findMovie()
        })

    }

    fun findMovie() {
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.random(MoviesDataItem(genre = category))
        call1.enqueue(object : Callback<MoviesDataItem?> {
            override fun onResponse(
                    call: Call<MoviesDataItem?>,
                    response: Response<MoviesDataItem?>
            ) {
                if (response.isSuccessful) {
                    Shimmer.stopShimmer()
//                    progressBar.visibility = View.GONE
                    Shimmer.visibility = View.GONE
                    val responseBody = response.body()!!
                    movieImage.load(BASEURL + responseBody.poster) {
                        placeholder(R.drawable.randomise_icon)
                        crossfade(true)
                    }
                    Toast.makeText(this@RandomMovie, responseBody.name, Toast.LENGTH_SHORT).show()
                    movieName.text = responseBody.name.toString()
                    getRating(responseBody._id.toString())
                    cardView.setOnClickListener {
                        val intent = Intent(this@RandomMovie, PlayMovie::class.java)
                        intent.putExtra("MOVIEID", responseBody._id)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(applicationContext, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getRating(movieId: String) {
        val request = ServiceBuilder.buildService()
        val call = request.rating(WishlistDataItem(Movieid = movieId))
        call.enqueue(object : Callback<String?> {
            override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        ratingBar.rating = 0F
                    } else {
                        ratingBar.rating = response.body()!!.toFloat()
                    }

                } else {
                    Toast.makeText(this@RandomMovie, "no rating", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                        .show()
            }
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.refreshList()
        call1.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
            ) {
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}