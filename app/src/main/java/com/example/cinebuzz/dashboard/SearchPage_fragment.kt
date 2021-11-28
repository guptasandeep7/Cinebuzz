package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.recyclerview.SearchPageAdapter
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.SearchHistoryDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPage_fragment : Fragment() {

    private var history = ArrayList<SearchHistoryDataItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchPageAdapter
    lateinit var searchEditText: TextInputEditText
    lateinit var searchText: TextView
    private lateinit var Shimmer: ShimmerFrameLayout
    var movies = ArrayList<MoviesDataItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = context
        val view = inflater.inflate(R.layout.search_page, container, false)
        recyclerView = view.findViewById(R.id.searchRecyclerView)

        searchEditText = view.findViewById(R.id.searchEditText)
        Shimmer = view.findViewById(R.id.SearchShimmer)
        searchText = view.findViewById(R.id.searchtext)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    SearchText(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        return view
    }

    fun SearchText(s: String) {

        movies.clear()
        val movies = ArrayList<MoviesDataItem>()
        val request = ServiceBuilder.buildService()
        val call = request.search(
            MoviesDataItem(
                name = s
            )
        )
        recyclerView.adapter = null
        Shimmer.stopShimmer()
        Shimmer.visibility = View.VISIBLE

        call.enqueue(
            object : Callback<List<MoviesDataItem>?> {
                override fun onResponse(
                    call: Call<List<MoviesDataItem>?>,
                    response: Response<List<MoviesDataItem>?>
                ) {
                    if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                        Shimmer.stopShimmer()
                        Shimmer.visibility = View.GONE
                        searchText.visibility=View.GONE
                        val responseBody: List<MoviesDataItem> = response.body()!!
                        for (movie in responseBody)
                            movies.add(movie)
                        adapter = SearchPageAdapter(activity, movies)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    } else  {
                        searchText.visibility=View.VISIBLE
                        Shimmer.stopShimmer()
                        Shimmer.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<List<MoviesDataItem>?>, t: Throwable) {
                    val fragmentManager = activity?.supportFragmentManager
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.search, SomthingWentWrong())
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            })
    }
}