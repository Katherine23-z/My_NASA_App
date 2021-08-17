package com.example.mynasaapplication.ui.main.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mynasaapplication.EARTH_FRAGMENT
import com.example.mynasaapplication.MARS_FRAGMENT
import com.example.mynasaapplication.WEATHER_FRAGMENT


class ViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayListOf(FragmentsData(EarthFragment(), "Earth"),
                                        FragmentsData(MarsFragment(), "Mars"),
                                        FragmentsData(MoonFragment(), "Weather")
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> fragments[EARTH_FRAGMENT].fragment
            1 -> fragments[MARS_FRAGMENT].fragment
            2 -> fragments[WEATHER_FRAGMENT].fragment
            else -> fragments[EARTH_FRAGMENT].fragment
        }
    }


}