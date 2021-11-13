package com.example.cinebuzz.dashboard.home
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.auth.LoginFragment
import com.example.cinebuzz.dashboard.HomePage_fragment
import com.example.cinebuzz.recyclerview.HomePageAdapter
import com.example.cinebuzz.recyclerview.TrendingPageAdapter
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingPage : AppCompatActivity() {

    private var movies= mutableListOf<MoviesDataItem>()
    private lateinit var recyclerView: RecyclerView
    private var gridLayoutManager:GridLayoutManager?=null
    private lateinit var adapter: TrendingPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trending)
        supportActionBar?.hide()
        val trendingText=findViewById<TextView>(R.id.trend)
        var category = intent.getStringExtra("Category")
        recyclerView=findViewById(R.id.TrendingRecyclerView)
        gridLayoutManager= GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        val request1 = ServiceBuilder.buildService()
        val call1 = when(category){
            "trending"-> {
                trendingText.text="Trending"
                request1.trending()

            }
            "action"-> {
                trendingText.text="Action"
                request1.action()
            }
            "comedy"-> {
                trendingText.text="Comedy"
                request1.comedy()
            }
            "horror"-> {
                trendingText.text="Horror"
                request1.horror()
            }
            "drama"->{
                trendingText.text="Drama"
                request1.drama()
            }
            else -> request1.trending()
        }


        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        movies.add(movie)
                    }
                    adapter= TrendingPageAdapter(this@TrendingPage,movies)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager=gridLayoutManager
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(this@TrendingPage,"failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            recyclerView.layoutManager=GridLayoutManager(applicationContext,3,LinearLayoutManager.VERTICAL,false)
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.layoutManager=GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        }
    }
}