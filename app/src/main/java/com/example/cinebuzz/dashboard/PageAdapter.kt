package com.example.cinebuzz.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment{
        return when(position) {
            0 -> {
                WishlistFragment()
            }
            1 -> {
                HistoryFragment()
            }
            else -> {
                WishlistFragment()
            }
        }
    }
}