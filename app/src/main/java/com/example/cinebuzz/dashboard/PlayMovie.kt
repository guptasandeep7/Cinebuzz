package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.profile.ProfilePageAdapter
import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayMovie : AppCompatActivity() {

    private lateinit var reviewsRecylcer : RecyclerView
    private var reviews= mutableListOf<ReviewDataItem>()
    private lateinit var adapter: ReviewsAdapter
    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_movie)

        val id = intent.getStringExtra("MOVIEID")
        reviewsRecylcer = findViewById(R.id.reviews_recyclerview)
        movieImage = findViewById(R.id.imageView3)
        movieName = findViewById(R.id.textView8)
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
                        movieImage.load(responseBody.posterurl) {
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


    }

