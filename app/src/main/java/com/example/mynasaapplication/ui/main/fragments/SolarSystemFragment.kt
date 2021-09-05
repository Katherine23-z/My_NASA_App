package com.example.mynasaapplication.ui.main.fragments

import android.graphics.Rect
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.mynasaapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.solar_system_layout.*

class SolarSystemFragment : Fragment() {

    private var show = false

    companion object {
        fun newInstance() = SolarSystemFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.solar_system_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab_earth.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.deep_sky_blue)
        fab_venus.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.indian_red)
        fab_jupiter.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.coral)
        fab_neptun.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.state_blue)
        fab_saturn.backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.peru)
        expandFab(fab_venus, expanded_container, R.color.maroon, R.color.indian_red)
        expandFab(fab_earth, expanded_container, R.color.deep_green, R.color.deep_sky_blue)
        expandFab(fab_jupiter, expanded_container, R.color.orange, R.color.coral)
        expandFab(fab_neptun, expanded_container, R.color.plum, R.color.state_blue)
        expandFab(fab_saturn, expanded_container, R.color.lime, R.color.peru)
        sun_fab.setOnClickListener {
            if (show) hideDescription() else showDescription()
        }
       }

    private fun expandFab(fab: FloatingActionButton, container: ViewGroup, color1 : Int, color2 : Int) {
        var isExpanded = false
        fab.setOnClickListener {
            TransitionManager.beginDelayedTransition(container)
            isExpanded = !isExpanded
            fab.size = if (isExpanded) FloatingActionButton.SIZE_NORMAL else FloatingActionButton.SIZE_MINI
            fab.backgroundTintList = if (isExpanded) AppCompatResources.getColorStateList(requireContext(), color1) else AppCompatResources.getColorStateList(requireContext(), color2)
        }
    }



    private fun showDescription(){
        show = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.solar_system_end)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 2000
        TransitionManager.beginDelayedTransition(expanded_container, transition)
        constraintSet.applyTo(expanded_container)
    }

    private fun hideDescription(){
        show = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.solar_system_layout)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 2000
        TransitionManager.beginDelayedTransition(expanded_container, transition)
        constraintSet.applyTo(expanded_container)
    }

}