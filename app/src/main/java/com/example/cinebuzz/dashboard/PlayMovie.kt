package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.profile.ProfilePageAdapter
import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import com.example.cinebuzz.retrofit.MoviesDataItem

class PlayMovie : AppCompatActivity() {

    private lateinit var reviewsRecylcer : RecyclerView
    private var reviews= mutableListOf<ReviewDataItem>()
    private lateinit var adapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_movie)

        //reviewsRecylcer = findViewById(R.id.reviews_recyclerview)

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


}