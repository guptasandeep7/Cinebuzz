package com.example.cinebuzz.dashboard.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.profile.ProfilePageAdapter
import com.example.cinebuzz.retrofit.MoviesDataItem

class WishlistFragment : Fragment() {

    lateinit var wishlistRecylcer : RecyclerView
    private var wishlist= ArrayList<MoviesDataItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        wishlistRecylcer = view.findViewById(R.id.wishlist_recyclerview)

//        val request1 = ServiceBuilder.buildService()
//        val call1 = request1.trending()
//        call1.enqueue(object : Callback<List<MoviesDataItem>?> {
//            override fun onResponse(call: Call<List<MoviesDataItem>?>, response: Response<List<MoviesDataItem>?>) {
//                if(response.isSuccessful) {
//
//                    Toast.makeText(context,"succeessfull", Toast.LENGTH_SHORT).show()
//                    val responseBody=response.body()!!
//                    for(item in responseBody) {
//                        wishlist.add(item)
//                    }
                    wishlistRecylcer.layoutManager = LinearLayoutManager(context)

                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlist.add(MoviesDataItem("Oblivian","1","https://d2kektcjb0ajja.cloudfront.net/images/posts/feature_images/000/000/072/large-1466557422-feature.jpg?1466557422"))
                    wishlistRecylcer.adapter = ProfilePageAdapter(wishlist,1)


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