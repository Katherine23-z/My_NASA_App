package com.example.mynasaapplication.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mynasaapplication.R

class SolarSystemFragment : Fragment() {

    companion object{
        fun newInstance() = SolarSystemFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.solar_system_layout, container, false)
    }
}