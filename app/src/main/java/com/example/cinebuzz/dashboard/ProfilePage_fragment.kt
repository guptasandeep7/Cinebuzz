package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.SplashScreen.Companion.USEREMAIL
import com.example.cinebuzz.SplashScreen.Companion.USERNAME
import com.example.cinebuzz.dashboard.profile.PageAdapter
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePage_fragment : Fragment() {

    lateinit var movieCount:TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager_UserProfile)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_UserProfile)
        movieCount=view.findViewById(R.id.movieCount)
        val pageAdapter = PageAdapter(this)
        viewPager.adapter = pageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Wishlist"
                1 -> tab.text = "History"
            }
        }.attach()
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_page, container, false)
        val context=context
        val userImage = view.findViewById<ShapeableImageView>(R.id.user_image)
        val userName = view.findViewById<TextView>(R.id.user_name)
        val userEmail = view.findViewById<TextView>(R.id.user_email)


        userName.text = USERNAME
        userEmail.text = USEREMAIL

        count()
        return view
    }
    fun count() {
        val request2 = ServiceBuilder.buildService()
        val call2 = request2.movieCount(
            WishlistDataItem(
                userid = SplashScreen.USERID
            )
        )
        call2.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    movieCount.text = responseBody
                } else {
                    Toast.makeText(
                        context,
                        "unsuccessful ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

}