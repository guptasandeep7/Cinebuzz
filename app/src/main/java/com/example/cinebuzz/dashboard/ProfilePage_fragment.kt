package com.example.cinebuzz.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.profile.PageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfilePage_fragment :Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager_UserProfile)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_UserProfile)

        val pageAdapter = PageAdapter(this)
        viewPager.adapter = pageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0->tab.text="Wishlist"
                1->tab.text="History"
            }
        }.attach()
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_page,container,false)
    }
}