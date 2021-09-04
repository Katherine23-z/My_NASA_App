package com.example.mynasaapplication.ui.main.api

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.mynasaapplication.R
import kotlinx.android.synthetic.main.moon_fragment.*
import kotlinx.android.synthetic.main.picture_of_yesterday_fragment.*

class MoonFragment: Fragment() {
    private var isExpanded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.moon_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      /*  photo_of_moon.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(moon_container, TransitionSet().addTransition(ChangeBounds()).addTransition(ChangeImageTransform()))

            val params: ViewGroup.LayoutParams = photo_of_moon.layoutParams
            params.height = if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            photo_of_moon.layoutParams = params
            photo_of_moon.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }*/

        val spannable = SpannableString("My text \nbullet one \nbullet two")
        spannable.setSpan(BulletSpan(40, Color.BLUE, 50), 9, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(resources.getColor(R.color.maroon)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        moon_text_view.text = spannable
    }

}
