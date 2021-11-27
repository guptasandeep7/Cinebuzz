package com.example.cinebuzz.dashboard.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.SplashScreen.Companion.USERID
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.ServiceBuilder2
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.facebook.shimmer.ShimmerFrameLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistFragment : Fragment() {
    companion object{
        lateinit var none2: TextView
    }

    lateinit var wishlistRecylcer: RecyclerView
    private val movieList = ArrayList<MoviesDataItem>()
    private lateinit var adapter: ProfilePageAdapter
    private lateinit var Shimmer: ShimmerFrameLayout
    lateinit var movieId: String

    lateinit var contex: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        contex = requireContext()
        wishlistRecylcer = view.findViewById(R.id.wishlist_recyclerview)
        Shimmer = view.findViewById(R.id.whislistShimmer)
       val image = view.findViewById<ImageButton>(R.id.wishlist_btn)
        none2 = view.findViewById(R.id.none2)
        none2.visibility = View.VISIBLE
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
                    val responseBody = response.body()!!
                    for (item in responseBody) {
                        profile_wishlist(item)
                    }
                } else {
                    Toast.makeText(
                        contex,
                        "unsuccessful ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<String>?>, t: Throwable) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.wish, SomthingWentWrong())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()            }
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
                    movieList.add(
                        MoviesDataItem(
                            name = responseBody.name,
                            poster = responseBody.poster,
                            _id = responseBody._id
                        )
                    )
                    adapter = ProfilePageAdapter(activity,movieList, 1)
                    wishlistRecylcer.adapter = adapter
                    wishlistRecylcer.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    if (wishlistRecylcer.adapter != null) {
                        none2.visibility=View.GONE
                    }
                } else {
                    Toast.makeText(contex, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.wish, SomthingWentWrong())
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
        })
    }
//    fun showWishlistt() {
//        val request3 = ServiceBuilder2.buildService()
//        val call3 = request3.wishlistCheck(
//            WishlistDataItem(
//                Movieid = movieId,
//                userid = PlayMovie.USERID
//            )
//        )
//        call3.enqueue(object : Callback<String?> {
//            override fun onResponse(
//                call: Call<String?>,
//                response: Response<String?>
//            ) {
//                if (response.isSuccessful) {
//                    when (response.body().toString()) {
//                        "1" -> image.setImageResource(R.drawable.ic_like_icon)
//                        "0" -> image.setImageResource(R.drawable.ic_vector__14_)
//                        else -> Toast.makeText(
//                            context,
//                            response.body().toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                } else {
//                    Toast.makeText(context, "not added to wishlist", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//
//            override fun onFailure(call: Call<String?>, t: Throwable) {
//                Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}