package com.example.mynasaapplication.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.mynasaapplication.*
import com.google.android.material.radiobutton.MaterialRadioButton

class SettingsFragment : Fragment() {
    private lateinit var redTheme: MaterialRadioButton
    private lateinit var springTheme: MaterialRadioButton
    private lateinit var defTheme: MaterialRadioButton
    private lateinit var greenTheme: MaterialRadioButton
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
        greenTheme = view.findViewById(R.id.green_theme)
        rg = view.findViewById(R.id.theme_radio_group)
        initRadioBtns()
        return view
    }

    fun initRadioBtns() {
        initRadioBtn(redTheme, RED_APP_THEME )
        initRadioBtn(springTheme, SPRING_THEME )
        initRadioBtn(defTheme, DEF_THEME )
        initRadioBtn(greenTheme, DEEP_GREEN_THEME )
        val currentStyle = getCodeStyle()

        checked = rg.children.first {  it.tag == currentStyle } as MaterialRadioButton

       // checked = rg.getChildAt(getCodeStyle())

        checked.isChecked = true
    }

    fun initRadioBtn(button: RadioButton, codeStyle: Int) {
        button.tag = codeStyle
        button.setOnClickListener {
            setAppTheme(codeStyle)
            requireActivity().recreate()
        }
    }

    fun getCodeStyle(): Int {
        val sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val index = sharedPref.getInt(APP_THEME, DEF_THEME)

        return index
    }

    fun setAppTheme(codeStyle: Int) {
        val sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putInt(APP_THEME, codeStyle)?.apply()
    }

}
