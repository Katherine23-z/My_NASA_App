package com.example.mynasaapplication.ui.main.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.transition.Explode
import android.transition.Transition
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.mynasaapplication.R
import kotlinx.android.synthetic.main.animatiom_fragment.*
import kotlinx.android.synthetic.main.fab_animation_layout.*

class AnimationFragment : Fragment() {
    private var textIsVisible = false
    private var toRightAnimation = false
    private var isExpanded = false


    companion object {
        fun newinstance() = AnimationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fab_animation_layout, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //anim_button.setOnClickListener {

        /*TransitionManager.beginDelayedTransition(anim_container, Slide(Gravity.BOTTOM))
        textIsVisible = !textIsVisible
        anim_text.visibility = if (textIsVisible) View.VISIBLE else View.GONE*/
        /* val changeBounds = ChangeBounds()
         changeBounds.setPathMotion(ArcMotion())
         changeBounds.duration = 1000
         TransitionManager.beginDelayedTransition(anim_container, changeBounds)
         toRightAnimation = !toRightAnimation
         val params = anim_button.layoutParams as FrameLayout.LayoutParams
         params.gravity = if(toRightAnimation) Gravity.END or Gravity.BOTTOM else Gravity.START or Gravity.TOP
         anim_button.layoutParams = params*/
        setFab()

    }

    private fun setFab() {
        setInitialState()
        fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFab()
            }
        }

    }

    private fun setInitialState() {
        transparent_background.apply {
            alpha = 0f
        }
        option_one_container.apply {
            alpha = 0f
            isClickable = false
        }
        option_two_container.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun collapseFab() {
        isExpanded = false
        ObjectAnimator.ofFloat(plus_imageview, "rotation", 0f, -180f).start()
        ObjectAnimator.ofFloat(option_two_container, "translationY", 0f).start()
        ObjectAnimator.ofFloat(option_one_container, "translationY", 0f).start()

        option_two_container.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        option_two_container.isClickable = false
                        option_one_container.setOnClickListener(null)
                    }
                })
        option_one_container.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        option_one_container.isClickable = false
                    }
                })
        transparent_background.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        transparent_background.isClickable = false
                    }
                })
    }


    private fun expandFab() {
        isExpanded = true
        ObjectAnimator.ofFloat(plus_imageview, "rotation", 0f, 225f).start()
        ObjectAnimator.ofFloat(option_two_container, "translationY", -130f).start()
        ObjectAnimator.ofFloat(option_one_container, "translationY", -250f).start()

        option_two_container.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        option_two_container.isClickable = true
                        option_two_container.setOnClickListener {
                            Toast.makeText(requireContext(), "Option 2", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        option_one_container.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        option_one_container.isClickable = true
                        option_one_container.setOnClickListener {
                            Toast.makeText(requireContext(), "Option 1", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        transparent_background.animate()
                .alpha(0.9f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        transparent_background.isClickable = true
                    }
                })
    }

}