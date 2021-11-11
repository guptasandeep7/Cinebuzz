package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.recyclerview.HomePageAdapter
import com.example.cinebuzz.recyclerview.SearchPageAdapter
import com.example.cinebuzz.recyclerview.TrendingPageAdapter
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.SearchHistoryDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPage_fragment :Fragment(){

    private var history= mutableListOf<SearchHistoryDataItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchPageAdapter
    override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {

        val view=inflater.inflate(R.layout.search_page,container,false)
        recyclerView=view.findViewById(R.id.searchRecyclerView)
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.history()
        call1.enqueue(object : Callback<List<SearchHistoryDataItem>?> {
            override fun onResponse(call: Call<List<SearchHistoryDataItem>?>, response: Response<List<SearchHistoryDataItem>?>) {
                if(response.isSuccessful) {

                    Toast.makeText(context,"succeessfull", Toast.LENGTH_SHORT).show()
                    val responseBody=response.body()!!
                    for(movie in responseBody) {
                        history.add(movie)
                    }
                    adapter= SearchPageAdapter(activity,history)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager=
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
                }
                else{
                    Toast.makeText(context,"un succeessfull ${response.message()}", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<SearchHistoryDataItem>?>, t: Throwable) {
                Toast.makeText(context,"failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    return view
}
}