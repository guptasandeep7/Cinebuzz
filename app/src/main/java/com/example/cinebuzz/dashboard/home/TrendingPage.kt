package com.example.cinebuzz.dashboard.home
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.recyclerview.TrendingPageAdapter
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingPage : AppCompatActivity() {

    private var movies= mutableListOf<MoviesDataItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var Shimmer: ShimmerFrameLayout
    private var gridLayoutManager:GridLayoutManager?=null
    private lateinit var adapter: TrendingPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trending)
        val toolbar:Toolbar = findViewById(R.id.toolbar8)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val trendingText=findViewById<TextView>(R.id.trend)
        var category = intent.getStringExtra("Category")
        recyclerView=findViewById(R.id.TrendingRecyclerView)
        Shimmer=findViewById(R.id.trendingShimmer)
        gridLayoutManager= GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        val request1 = ServiceBuilder.buildService()
        val call1 = when(category){
            "trending"-> {
                trendingText.text="Trending"
                request1.trending()

            }
            "action"-> {
                trendingText.text="Action"
                request1.action("Action")
            }
            "comedy"-> {
                trendingText.text="Comedy"
                request1.action("Comedy")
            }
            "horror"-> {
                trendingText.text="Horror"
                request1.action("Horrer")
            }
            "drama"->{
                trendingText.text="Drama"
                request1.action("Drama")
            }
            else -> request1.trending()
        }


        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {
                    Shimmer.stopShimmer()
                    Shimmer.visibility= View.GONE
                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        movies.add(movie)
                    }
                    adapter= TrendingPageAdapter(this@TrendingPage,movies)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager=gridLayoutManager
                    adapter.setOnItemClickListener(object :TrendingPageAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent=Intent(this@TrendingPage, PlayMovie::class.java)
                            intent.putExtra("MOVIEID",adapter.movies[position]._id)
                            startActivity(intent)
                        }
                    })
                }

            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.trendg, SomthingWentWrong())
                transaction.commit()            }
        })



    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            recyclerView.layoutManager=GridLayoutManager(applicationContext,4,LinearLayoutManager.VERTICAL,false)
        }
        else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.layoutManager=GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        }
    }
}