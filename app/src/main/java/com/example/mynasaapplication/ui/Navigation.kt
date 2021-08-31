package com.example.mynasaapplication.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import com.example.mynasaapplication.R

class Navigation(val manager : FragmentManager, val container: Int) {
    fun addFragment(fragment : Fragment?, useBackstack : Boolean) {
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.replace(container, fragment!!)
        if (useBackstack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

}