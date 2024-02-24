package com.example.moviestore

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviestore.fragments.AboutFragment
import com.example.moviestore.fragments.FavFragment
import com.example.moviestore.fragments.MovieListFragment
import com.example.moviestore.fragments.SettingFragment

class OrdersPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieListFragment()
            1 -> FavFragment()
            2 -> SettingFragment()
            else -> AboutFragment()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}