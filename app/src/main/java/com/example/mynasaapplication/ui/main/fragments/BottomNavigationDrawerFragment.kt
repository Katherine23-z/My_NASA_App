package com.example.mynasaapplication.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mynasaapplication.BottomNavigationApiActivity
import com.example.mynasaapplication.MainActivity
import com.example.mynasaapplication.R
import com.example.mynasaapplication.ui.Navigation
import com.example.mynasaapplication.ui.main.api.ApiFragment
import com.example.mynasaapplication.ui.main.api.ui.NoteFragment
import com.example.mynasaapplication.ui.main.api.ui.canvasExperiment.CanvasActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private lateinit var navView : NavigationView
    private lateinit var navigation: Navigation


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.navigation_drawer, container, false)
        navView = view.findViewById(R.id.navigation_view)
        val activity: MainActivity = context as MainActivity
        navigation = activity.getNavigation()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navView.setNavigationItemSelectedListener {menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_one -> activity?.let { startActivity(Intent(it, BottomNavigationApiActivity::class.java)) }
                R.id.navigation_two -> navigation.addFragment(ApiFragment(), true)
                R.id.navigation_three -> navigation.addFragment(NoteFragment(), true)
                R.id.navigation_four -> activity?.let { startActivity(Intent(it, CanvasActivity::class.java)) }
            }
            true
        }
    }
}