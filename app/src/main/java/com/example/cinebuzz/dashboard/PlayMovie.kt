package com.example.cinebuzz.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.Play
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayMovie : AppCompatActivity() {

    private lateinit var reviewsRecylcer : RecyclerView
    private var reviews= mutableListOf<ReviewDataItem>()
    private lateinit var adapter: ReviewsAdapter
    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    lateinit var rating:RatingBar
    lateinit var movieId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_movie)

        val id = intent.getStringExtra("MOVIEID")
        reviewsRecylcer = findViewById(R.id.reviews_recyclerview)
        movieImage = findViewById(R.id.imageView3)
        movieName = findViewById(R.id.textView8)
        rating = findViewById(R.id.movie_rating)
        val playBtn = findViewById<ImageView>(R.id.playImage)



            val request1 = ServiceBuilder.buildService()
            val call1 = request1.movie(
                MoviesDataItem(
                    _id = id
                )
            )
            call1.enqueue(object : Callback<MoviesDataItem?> {
                override fun onResponse(
                    call: Call<MoviesDataItem?>,
                    response: Response<MoviesDataItem?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()!!
                        movieImage.load(BASEURL+responseBody.poster.toString()){
                            placeholder(R.drawable.randomise_icon)
                            crossfade(true)
                        }
                        movieName.text = responseBody.name
                        movieId = responseBody._id!!
                        getrating()
                        playBtn.setOnClickListener{
                            val intent = Intent(applicationContext, Play::class.java)
                            intent.putExtra("VIDEOURL",responseBody.video.toString())
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
//        val request1 = ServiceBuilder.buildService()
//        val call1 = request1.trending()
//        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
//            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
//                if(response.isSuccessful) {
//
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
//
//                }
//            }
//
//            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
//                Toast.makeText(context,"failed ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })

        fun getrating(){
            val request = ServiceBuilder.buildService()
            val call = request.rating(movieId)
            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?:0
                        Toast.makeText(this@PlayMovie,responseBody.toString(),Toast.LENGTH_SHORT).show()
                        rating.rating = responseBody as Float

                    }
                    else{
                        Toast.makeText(this@PlayMovie,"no rating",Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

