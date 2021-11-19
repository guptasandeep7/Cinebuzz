package com.example.cinebuzz.dashboard.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.USERID
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistFragment : Fragment() {

    lateinit var wishlistRecylcer: RecyclerView
    private val movieList = ArrayList<MoviesDataItem>()
    private lateinit var adapter: ProfilePageAdapter
    private lateinit var Shimmer: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        val context = context
        wishlistRecylcer = view.findViewById(R.id.wishlist_recyclerview)
        Shimmer=view.findViewById(R.id.whislistShimmer)
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.wishlistAll(
            WishlistDataItem(userid = USERID)
        )
        call1.enqueue(object : Callback<ArrayList<String>?> {
            override fun onResponse(
                call: Call<ArrayList<String>?>,
                response: Response<ArrayList<String>?>
            ) {
                if (response.isSuccessful) {
                    Shimmer.stopShimmer()
                    Shimmer.visibility = View.GONE
                    Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show()
                    val responseBody = response.body()!!
                    for (item in responseBody) {
                        profile_wishlist(item)
                    }
                    adapter = ProfilePageAdapter(movieList, 1)
                    wishlistRecylcer.adapter = adapter
                    wishlistRecylcer.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


                } else {
                    Toast.makeText(
                        context,
                        "unsuccessful ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            override fun onFailure(call: Call<ArrayList<String>?>, t: Throwable) {
                Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }

    fun profile_wishlist(id: String) {
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
                    Toast.makeText(context, responseBody.toString(), Toast.LENGTH_SHORT).show()
                    movieList.add(responseBody)
                } else {
                    Toast.makeText(context, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}