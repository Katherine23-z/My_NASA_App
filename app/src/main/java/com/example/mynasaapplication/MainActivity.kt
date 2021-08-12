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
        setTheme(getAppTheme(RED_APP_THEME))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = Navigation(supportFragmentManager, R.id.container)
        navigation.addFragment(PictureOfTheDayFragment.newInstance(), false)
    }

    fun getNavigation(): Navigation {
        return navigation
    }

    fun codeStyleToStyleId(codeStyle: Int) : Int{
        when (codeStyle) {
            RED_APP_THEME -> return R.style.RedTheme
            SPRING_THEME -> return R.style.SpringTheme
            DEF_THEME -> return R.style.Theme_MyNASAApplication
        }
        return R.style.Theme_MyNASAApplication
    }
    fun getCodeStyle(codeStyle: Int): Int {
        val sharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return sharedPref.getInt(APP_THEME, codeStyle)
    }

    fun getAppTheme(codeStyle: Int) : Int{
        return codeStyleToStyleId(getCodeStyle(codeStyle))
    }
}