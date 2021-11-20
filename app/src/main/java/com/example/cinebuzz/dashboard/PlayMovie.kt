package com.example.cinebuzz.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.MainActivity
import com.example.cinebuzz.Play
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.ServiceBuilder2
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayMovie : AppCompatActivity() {
    companion object {
        val BASEURL = "https://cine---buzz.herokuapp.com/"
        lateinit var USERNAME: String
        lateinit var USEREMAIL: String
        lateinit var USERID: String
        lateinit var DPURL: String
        lateinit var TOKEN: String
        lateinit var dataStore: DataStore<Preferences>
        suspend fun logInState(value: Boolean) {
            val dataStoreKey = preferencesKey<Boolean>("LOGIN")
            dataStore.edit { UserDetails ->
                UserDetails[dataStoreKey] = value
            }
        }

        suspend fun saveUserDetails(key: String, value: String) {
            val dataStoreKey = preferencesKey<String>(key)
            dataStore.edit { UserDetails ->
                UserDetails[dataStoreKey] = value
            }

        }

        suspend fun isLogedIn(): Boolean? {
            val dataStoreKey = preferencesKey<Boolean>("LOGIN")
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

        suspend fun getUserDetails(key: String): String? {
            val dataStoreKey = preferencesKey<String>(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

    }

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
    lateinit var shareBtn: ImageView
    lateinit var playBtn: ImageView
    lateinit var rateThisMovie: TextView
    lateinit var ratingBar2: RatingBar
    lateinit var writeReview: TextView
    lateinit var playEditText: TextInputLayout
    lateinit var review: TextInputEditText
    lateinit var submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_movie)

        dataStore = applicationContext?.createDataStore(name = "UserDetails")!!
        lifecycleScope.launch {
            if (isLogedIn() == true) {
                USERNAME = getUserDetails("USERNAME")!!
                USEREMAIL = getUserDetails("USEREMAIL")!!
                TOKEN = getUserDetails("TOKEN")
                    ?: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdvbGNoaWdlZWtAZ21haWwuY29tIiwiaWF0IjoxNjM3MzUxMDQ4LCJleHAiOjE2Mzc0Mzc0NDh9.v1fuXxizIYD4cwzca_hZCS9CSVObUMbzqror4hQ6YUY"
                USERID = getUserDetails("USERID")!!
            } else {
                TOKEN =
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdvbGNoaWdlZWtAZ21haWwuY29tIiwiaWF0IjoxNjM3MzUxMDQ4LCJleHAiOjE2Mzc0Mzc0NDh9.v1fuXxizIYD4cwzca_hZCS9CSVObUMbzqror4hQ6YUY"
                val intent = Intent(this@PlayMovie, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        reviewsRecylcer = findViewById(R.id.reviews_recyclerview)
        movieImage = findViewById(R.id.imageView3)
        movieName = findViewById(R.id.textView8)
        Shimmer = findViewById(R.id.playshimmer)
        plot = findViewById(R.id.movie_plot)
        rating = findViewById(R.id.movie_rating)
        wishlist = findViewById(R.id.wishlist)
        shareBtn = findViewById(R.id.share_btn)
        playBtn = findViewById(R.id.playImage)
        rateThisMovie = findViewById(R.id.rate_this_movie)
        ratingBar2 = findViewById(R.id.ratingBar2)
        writeReview = findViewById(R.id.textView9)
        playEditText = findViewById(R.id.playEdittext)
        review = findViewById(R.id.review)
        submit = findViewById(R.id.submit)


        handleIntent(intent)
        shareBtn.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "https://cine---buzz.herokuapp.com/movieid/$movieId"
            )
            shareIntent.type = "text/*"
            startActivity(Intent.createChooser(shareIntent, "share movie to"))
        }


        rateThisMovie.setOnClickListener {
            if (ratingBar2.isVisible) {
                ratingBar2.visibility = View.GONE
                writeReview.visibility = View.GONE
                playEditText.visibility = View.GONE
                review.visibility = View.GONE
                submit.visibility = View.GONE
            } else {
                ratingBar2.visibility = View.VISIBLE
                writeReview.visibility = View.VISIBLE
                playEditText.visibility = View.VISIBLE
                review.visibility = View.VISIBLE
                submit.visibility = View.VISIBLE
            }
        }
// wishlistToggle

        submit.setOnClickListener {
            if (ratingBar2.rating != 0F) {
                sendRating()
                if (review.text.toString().isNotEmpty())
                    sendReview()
                ratingBar2.visibility = View.GONE
                writeReview.visibility = View.GONE
                playEditText.visibility = View.GONE
                review.visibility = View.GONE
                submit.visibility = View.GONE
            } else {
                review.error = "Please rate the movie"
            }

        }

        wishlist.setOnClickListener {
            val request2 = ServiceBuilder2.buildService()
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
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@PlayMovie, "failed ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        }

//  show wishlist
//        val request1 = ServiceBuilder2.buildService()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.lastPathSegment?.also { movieid ->
                Uri.parse("content://com.example.cinebuzz/movieid/")
                    .buildUpon()
                    .appendPath(movieid)
                    .build().also {
                        movieId = movieid
                    }
            }
        } else {
            movieId = intent.getStringExtra("MOVIEID").toString()
        }
        showMovie()
    }

    fun sendRating() {
        val request = ServiceBuilder2.buildService()
        val call = request.sendRating(
            WishlistDataItem(
                Movieid = movieId,
                userid = USERID,
                rating = ratingBar2.rating.toString()
            )
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PlayMovie, ratingBar2.rating.toString(), Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(this@PlayMovie, "no rating", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun userDetails(userId: String, reviewText: String) {
        val review = ArrayList<ReviewDataItem>()
        val request = ServiceBuilder2.buildService()
        val call = request.userDetails(WishlistDataItem(userid = userId))
        call.enqueue(object : Callback<ReviewDataItem?> {
            override fun onResponse(
                call: Call<ReviewDataItem?>,
                response: Response<ReviewDataItem?>
            ) {
                if (response.isSuccessful && response.body()!=null) {
                    val responseBody = response.body()!!
                    review.add(
                        ReviewDataItem(
                            name = responseBody.name,
                            dpUrl = responseBody.dpUrl ?: "Nan",
                            reviewText = reviewText
                        )
                    )
                    adapter = ReviewsAdapter(review)
                    reviewsRecylcer.adapter = adapter
                    reviewsRecylcer.layoutManager =
                        LinearLayoutManager(this@PlayMovie, LinearLayoutManager.VERTICAL, false)
                } else {
                    Toast.makeText(this@PlayMovie, "no review", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ReviewDataItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showReview() {
        val review: ArrayList<ReviewDataItem>
        val request = ServiceBuilder2.buildService()
        val call = request.showReview(WishlistDataItem(Movieid = movieId))
        call.enqueue(object : Callback<ArrayList<WishlistDataItem>?> {
            override fun onResponse(
                call: Call<ArrayList<WishlistDataItem>?>,
                response: Response<ArrayList<WishlistDataItem>?>
            ) {
                if (response.isSuccessful) {
                    for (item in response.body()!!) {
                        userDetails(item.userid!!, item.review!!)
                    }

                } else {
                    Toast.makeText(this@PlayMovie, "no review", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ArrayList<WishlistDataItem>?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun sendReview() {
        val request = ServiceBuilder2.buildService()
        val call = request.sendReview(
            WishlistDataItem(
                Movieid = movieId,
                userid = USERID,
                review = review.text.toString()
            )
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PlayMovie, ratingBar2.rating.toString(), Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(this@PlayMovie, "no rating", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showMovie() {
        //movie Details
        val request1 = ServiceBuilder2.buildService()
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
                    showReview()
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
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    fun showWishlist() {
        val request3 = ServiceBuilder2.buildService()
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
        val request = ServiceBuilder2.buildService()
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


