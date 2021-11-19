package com.example.cinebuzz.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.Play
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.SplashScreen.Companion.USERID
import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayMovie : AppCompatActivity() {

    private lateinit var reviewsRecylcer: RecyclerView
    private lateinit var Shimmer: ShimmerFrameLayout
    private lateinit var reviews: ArrayList<ReviewDataItem>
    private lateinit var adapter: ReviewsAdapter
    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    lateinit var rating: RatingBar
    lateinit var movieId: String
    lateinit var wishlist: ImageView
    lateinit var plot: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_movie)

        movieId = intent.getStringExtra("MOVIEID").toString()
        reviewsRecylcer = findViewById(R.id.reviews_recyclerview)
        movieImage = findViewById(R.id.imageView3)
        movieName = findViewById(R.id.textView8)
        Shimmer = findViewById(R.id.playshimmer)
        plot = findViewById(R.id.movie_plot)
        rating = findViewById(R.id.movie_rating)
        wishlist = findViewById(R.id.wishlist)
        val playBtn = findViewById<ImageView>(R.id.playImage)

//movie Details
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.movie(
            MoviesDataItem(
                _id = movieId
            )
        )
        call1.enqueue(object : Callback<MoviesDataItem?> {
            override fun onResponse(
                call: Call<MoviesDataItem?>,
                response: Response<MoviesDataItem?>
            ) {
                if (response.isSuccessful) {
                    Shimmer.stopShimmer()
                    Shimmer.visibility = View.GONE
                    val responseBody = response.body()!!
                    movieImage.load(BASEURL + responseBody.poster.toString()) {
                        placeholder(R.drawable.randomise_icon)
                        crossfade(true)
                    }
                    movieName.text = responseBody.name
                    plot.text = responseBody.plot
                    showWishlist()
                    getRating()
                    playBtn.setOnClickListener {
                        addToHistory()
                        val intent = Intent(applicationContext, Play::class.java)
                        intent.putExtra("VIDEOURL", responseBody.video.toString())
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No movie found" + response.code(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

// wishlistToggle

        wishlist.setOnClickListener {
            val request2 = ServiceBuilder.buildService()
            val call2 =
                request2.wishlistToggle(WishlistDataItem(Movieid = movieId, userid = USERID))
            call2.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    when {
                        response.isSuccessful -> {
                            wishlist.setImageResource(R.drawable.ic_frame__1_)
                        }
                        response.code() == 301 -> {
                            wishlist.setImageResource(R.drawable.ic_frame)
                        }
                        else -> {
                            Toast.makeText(
                                this@PlayMovie,
                                response.body().toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@PlayMovie, "failed ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }

//  show wishlist
//        val request1 = ServiceBuilder.buildService()
//        val call1 = request1.trending()
//        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
//            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
//                if(response.isSuccessful) {
//                    Toast.makeText(context,"successful", Toast.LENGTH_SHORT).show()
//                    val responseBody=response.body()!!
//                    for(item in responseBody) {
//                        historyList.add(item)
//                    }
//        adapter= ReviewsAdapter(reviews)
//        reviewsRecylcer.adapter=adapter
//        reviewsRecylcer.layoutManager=
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
//                }
//                else{
//                    Toast.makeText(context,"unsuccessful ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
//                Toast.makeText(context,"failed ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    fun showWishlist() {
        val request3 = ServiceBuilder.buildService()
        val call3 = request3.wishlistCheck(
            WishlistDataItem(
                Movieid = movieId,
                userid = USERID
            )
        )
        call3.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                if (response.isSuccessful) {
                    when (response.body().toString()) {
                        "1" -> wishlist.setImageResource(R.drawable.ic_frame__1_)
                        "0" -> wishlist.setImageResource(R.drawable.ic_frame)
                        else -> Toast.makeText(
                            this@PlayMovie,
                            response.body().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this@PlayMovie, "not added to wishlist", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@PlayMovie, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getRating() {
        val request = ServiceBuilder.buildService()
        val call = request.rating(WishlistDataItem(Movieid = movieId))
        call.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        rating.rating = 0F
                    } else {
                        rating.rating = response.body()!!.toFloat()
                    }

                } else {
                    Toast.makeText(this@PlayMovie, "no rating", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun addToHistory() {
        Toast.makeText(this@PlayMovie, movieId, Toast.LENGTH_SHORT).show()
        Toast.makeText(this@PlayMovie, USERID, Toast.LENGTH_SHORT).show()

        val request4 = ServiceBuilder.buildService()
        val call4 = request4.history(
            WishlistDataItem(
                Movieid = movieId,
                userid = USERID
            )
        )
        call4.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@PlayMovie, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

