package com.example.cinebuzz.dashboard

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomMovie : AppCompatActivity() {

    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    lateinit var nextBtn: Button
    lateinit var category: String
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
        movieName = findViewById(R.id.movie_name)
        nextBtn = findViewById(R.id.button)
        Shimmer =findViewById(R.id.randomshimmer)
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
                    Shimmer.visibility = View.GONE
                    val responseBody = response.body()!!
                    movieImage.load(BASEURL+responseBody.poster) {
                        placeholder(R.drawable.randomise_icon)
                        crossfade(true)
                    }
                    movieName.text = responseBody.name
                } else {
                    Toast.makeText(applicationContext, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}