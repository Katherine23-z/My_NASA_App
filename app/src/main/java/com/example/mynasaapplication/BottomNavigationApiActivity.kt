package com.example.mynasaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mynasaapplication.ui.Navigation
import com.example.mynasaapplication.ui.main.api.EarthFragment
import com.example.mynasaapplication.ui.main.api.MarsFragment
import com.example.mynasaapplication.ui.main.api.MoonFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationApiActivity : AppCompatActivity() {
    private lateinit var navigation: Navigation


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MyBottomActivityTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_api)
        navigation = Navigation(supportFragmentManager, R.id.activity_api_bottom_container)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navig_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    navigation.addFragment(EarthFragment(), true)
                    true
                }
                R.id.bottom_view_mars -> {
                    navigation.addFragment(MarsFragment(), true)
                    true
                }
                R.id.bottom_view_moon -> {
                    navigation.addFragment(MoonFragment(), true)
                    true
                }
                else -> false
            }
        }

        navView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                   Toast.makeText(this, getString(R.string.already_tapped), Toast.LENGTH_SHORT).show()
                }
                R.id.bottom_view_mars -> {

                    Toast.makeText(this, getString(R.string.already_tapped), Toast.LENGTH_SHORT).show()
                }
                R.id.bottom_view_moon -> {

                    Toast.makeText(this, getString(R.string.already_tapped), Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}

