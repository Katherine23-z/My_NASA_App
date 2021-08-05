package com.example.mynasaapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynasaapplication.databinding.MainActivityBinding
import com.example.mynasaapplication.ui.Navigation
import com.example.mynasaapplication.ui.main.fragments.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navigation : Navigation
    private lateinit var binding : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = Navigation(supportFragmentManager)
        navigation.addFragment(PictureOfTheDayFragment.newInstance(), false)
    }

    fun getNavigation(): Navigation {
        return navigation
    }
}