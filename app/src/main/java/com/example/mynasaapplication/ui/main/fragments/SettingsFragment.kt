package com.example.mynasaapplication.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.mynasaapplication.R
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.android.synthetic.main.settings_fragment.*

private const val SHARED_PREF_NAME = "THEME_COLOR"
private const val APP_THEME = "APP_THEME"
private const val RED_APP_THEME = 1
private const val DEF_THEME = 0
private const val SPRING_THEME = 2

class SettingsFragment : Fragment() {
    private lateinit var redTheme: MaterialRadioButton
    private lateinit var springTheme: MaterialRadioButton
    private lateinit var defTheme: MaterialRadioButton
    private lateinit var rg : RadioGroup
    private lateinit var checked : MaterialRadioButton

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        redTheme = view.findViewById(R.id.red_theme)
        springTheme = view.findViewById(R.id.spring_theme)
        defTheme = view.findViewById(R.id.def_theme)
        rg = view.findViewById(R.id.theme_radio_group)
        initRadioBtns()
        return view
    }

    fun initRadioBtns() {
        initRadioBtn(redTheme, RED_APP_THEME )
        initRadioBtn(springTheme, SPRING_THEME )
        initRadioBtn(defTheme, DEF_THEME )
        checked = rg.getChildAt(getCodeStyle(RED_APP_THEME)) as MaterialRadioButton
        checked.isChecked
    }

    fun initRadioBtn(button: RadioButton, codeStyle: Int) {
        button.setOnClickListener {
            setAppTheme(codeStyle)
            requireActivity().recreate()
        }
    }

    fun getCodeStyle(codeStyle: Int): Int {
        val sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(APP_THEME, codeStyle)
    }

    fun setAppTheme(codeStyle: Int) {
        val sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putInt(APP_THEME, codeStyle)?.apply()
    }

}
