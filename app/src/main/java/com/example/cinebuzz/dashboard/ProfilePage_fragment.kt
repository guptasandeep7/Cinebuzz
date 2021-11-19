package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.USEREMAIL
import com.example.cinebuzz.SplashScreen.Companion.USERNAME
import com.example.cinebuzz.dashboard.profile.PageAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfilePage_fragment : Fragment() {

    companion object {
        lateinit var clearAll: TextView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager_UserProfile)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_UserProfile)

        clearAll = view.findViewById<TextView>(R.id.clearAll)

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

        val userImage = view.findViewById<ShapeableImageView>(R.id.user_image)
        val userName = view.findViewById<TextView>(R.id.user_name)
        val userEmail = view.findViewById<TextView>(R.id.user_email)


        userName.text = USERNAME
        userEmail.text = USEREMAIL

        return view
    }
}