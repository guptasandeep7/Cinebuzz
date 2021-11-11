package com.example.cinebuzz.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MoviesDataItem

class HistoryFragment : Fragment() {

    lateinit var historyRecylcer : RecyclerView
    private var historyList= mutableListOf<MoviesDataItem>()
    private lateinit var adapter:ProfilePageAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecylcer = view.findViewById(R.id.history_recyclerview)

//        val request1 = ServiceBuilder.buildService()
//        val call1 = request1.trending()
//        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
//            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
//                if(response.isSuccessful) {
//
//                    Toast.makeText(context,"succeessfull", Toast.LENGTH_SHORT).show()
//                    val responseBody=response.body()!!
//                    for(item in responseBody) {
//                        historyList.add(item)
//                    }
        adapter= ProfilePageAdapter(historyList,2)
        historyRecylcer.adapter=adapter
        historyRecylcer.layoutManager=
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
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


        return view
    }


}