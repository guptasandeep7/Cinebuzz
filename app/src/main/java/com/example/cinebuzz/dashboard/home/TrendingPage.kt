package com.example.cinebuzz.dashboard.home
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        recyclerView=findViewById(R.id.TrendingRecyclerView)
        gridLayoutManager= GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false)
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.trending()
        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
                if(response.isSuccessful) {

                    Toast.makeText(this@TrendingPage,"succeessfull", Toast.LENGTH_SHORT).show()
                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        movies.add(movie)
                    }
                    adapter= TrendingPageAdapter(this@TrendingPage,movies)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager=gridLayoutManager
                }
                else{
                    Toast.makeText(this@TrendingPage,"un succeessfull ${response.message()}", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                Toast.makeText(this@TrendingPage,"failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



    }
}