package com.example.mynasaapplication.ui.main.api

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.mynasaapplication.MainActivity
import com.example.mynasaapplication.R
import com.example.mynasaapplication.ui.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import me.relex.circleindicator.CircleIndicator
import me.relex.circleindicator.CircleIndicator3

class ApiFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var indicator: CircleIndicator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.api_fragment, container, false)
        viewPager = view.findViewById(R.id.view_pager_widget)
        indicator = view.findViewById(R.id.indicator)
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        indicator.setViewPager(viewPager)
        return view
    }


}