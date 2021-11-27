package com.example.cinebuzz.dashboard.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    private lateinit var historyRecylcer: RecyclerView
    private var historyList = ArrayList<MoviesDataItem>()
    private lateinit var adapter: ProfilePageAdapter
    private lateinit var Shimmer: ShimmerFrameLayout
    lateinit var clearAll: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyRecylcer = view.findViewById(R.id.history_recyclerview)
        Shimmer = view.findViewById(R.id.historyShimmer)
        clearAll = view.findViewById(R.id.clearAll)
        clearAll.visibility = View.GONE

        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("History")
            .setMessage("Do you want to clear history ?")
            .setPositiveButton(R.string.clear_history) { dialog, id -> clearAll() }
            .setNeutralButton(R.string.cancel) { dialog, id -> }

        val request1 = ServiceBuilder.buildService()
        val call1 = request1.showHistory(
            WishlistDataItem(userid = SplashScreen.USERID)
        )
        call1.enqueue(object : Callback<ArrayList<String>?> {
            override fun onResponse(
                call: Call<ArrayList<String>?>,
                response: Response<ArrayList<String>?>
            ) {
                if (response.isSuccessful) {
                    Shimmer.stopShimmer()
                    Shimmer.visibility = View.GONE
                    val responseBody = response.body()!!
                    for (item in responseBody) {
                        profile_history(item)
                    }

                } else {
                    Toast.makeText(
                        context,
                        "unsuccessful ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<ArrayList<String>?>, t: Throwable) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.hist, SomthingWentWrong())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()            }
        })

        clearAll.setOnClickListener {
            val alertDialog: android.app.AlertDialog = builder.create()
            alertDialog.show()
        }

        return view
    }


    fun profile_history(id: String) {
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
                    historyList.add(
                        MoviesDataItem(
                            name = responseBody.name,
                            poster = responseBody.poster
                        )

                    )
                    adapter = ProfilePageAdapter(activity, historyList, 2)
                    historyRecylcer.adapter = adapter
                    historyRecylcer.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    if (historyRecylcer.adapter != null)
                        clearAll.visibility = View.VISIBLE
                } else {
                    Toast.makeText(context, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.hist, SomthingWentWrong())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()            }
        })
    }

    fun clearAll() {

        val request2 = ServiceBuilder.buildService()
        val call2 = request2.deleteHistory(
            WishlistDataItem(userid = SplashScreen.USERID)
        )
        call2.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                if (response.isSuccessful) {
                    clearAll.visibility = View.GONE
                    Toast.makeText(context, "History Cleared", Toast.LENGTH_SHORT).show()
                    historyList.clear()
                } else {
                    Toast.makeText(
                        context,
                        "unsuccessful ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.hist, SomthingWentWrong())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()            }
        })
    }

}