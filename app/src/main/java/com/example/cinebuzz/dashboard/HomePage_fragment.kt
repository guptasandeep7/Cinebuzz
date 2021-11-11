package com.example.cinebuzz.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.home.TrendingPage
import com.example.cinebuzz.recyclerview.HomePageAdapter
import com.example.cinebuzz.retrofit.Latest
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.MoviesDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage_fragment :Fragment(){

   private var movies= mutableListOf<MoviesDataItem>()
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView
    private lateinit var recyclerView5: RecyclerView

    private lateinit var adapter:HomePageAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_page, container, false)
        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()
        val request = ServiceBuilder.buildService()
        val call = request.latest()
        call.enqueue(object : Callback<List<Latest>?> {
            override fun onResponse(call: Call<List<Latest>?>, response: Response<List<Latest>?>) {
                if(response.isSuccessful) {

                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        imageList.add(SlideModel(movie.posterurl,ScaleTypes.FIT))

                    }
                    imageSlider.setImageList(imageList)
                }
                else{
                    Toast.makeText(context,"un succeessfull ${response.message()}",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<Latest>?>, t: Throwable) {
                Toast.makeText(context,"${t.message}",Toast.LENGTH_SHORT).show()
            }
        })


        recyclerView1=view.findViewById(R.id.Trending)
        recyclerView2=view.findViewById(R.id.Action)
        recyclerView3=view.findViewById(R.id.Horror)
        recyclerView4=view.findViewById(R.id.Comedy)
        recyclerView5=view.findViewById(R.id.Drama)
//        recyclerView1.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView2.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView3.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView4.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView5.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView1.adapter = HomePageAdapter(context,movies)
//        recyclerView2.adapter=HomePageAdapter(context,movies)
//        recyclerView3.adapter=HomePageAdapter(context,movies)
//        recyclerView4.adapter=HomePageAdapter(context,movies)
//        recyclerView5.adapter=HomePageAdapter(context,movies)


       val request1 = ServiceBuilder.buildService()
       val call1 = request1.trending()
       call1.enqueue(object : Callback<List<MoviesDataItem>?> {
           override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
               if(response.isSuccessful) {

                   Toast.makeText(context,"succeessfull",Toast.LENGTH_SHORT).show()
                   val responseBody=response.body()!!
                   for(movie in responseBody) {
                       movies.add(movie)
                   }
                   adapter= HomePageAdapter(activity,movies)
                   recyclerView1.adapter=adapter
                   recyclerView1.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
               }
               else{
                   Toast.makeText(context,"un succeessfull ${response.message()}",Toast.LENGTH_SHORT).show()

               }
           }

           override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
               Toast.makeText(context,"failed ${t.message}",Toast.LENGTH_SHORT).show()
           }
       })



//        imageList.add(SlideModel("https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://live.staticflickr.com/1980/29996141587_7886795726_b.jpg",ScaleTypes.FIT))
//        imageSlider.setImageList(imageList)

        val Arrow1=view.findViewById<ImageView>(R.id.Arrow1)
        val trending=view.findViewById<TextView>(R.id.TrendingText)
        Arrow1.setOnClickListener {
            startActivity(Intent(activity, TrendingPage::class.java))
        }
        return view
    }
}