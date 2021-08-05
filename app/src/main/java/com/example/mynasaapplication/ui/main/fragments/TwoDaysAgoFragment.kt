package com.example.mynasaapplication.ui.main.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.PictureOfTheDayData
import com.example.mynasaapplication.ui.main.viewModel.TwoDaysAgoViewModel
import kotlinx.android.synthetic.main.picture_of_yesterday_fragment.*
import kotlinx.android.synthetic.main.two_days_ago_fragment.*

class TwoDaysAgoFragment : Fragment() {

    companion object{
        fun newInstance() = TwoDaysAgoFragment()
    }

    private val viewModel: TwoDaysAgoViewModel by lazy {
        ViewModelProvider(this).get(TwoDaysAgoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.two_days_ago_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})

    }

    fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val myUrl= serverResponseData.url
                val description = serverResponseData.explanation
                if (myUrl.isNullOrEmpty()) {
                    Toast.makeText(context, "Ссылка пустая", Toast.LENGTH_SHORT).show()
                } else {
                    twoDaysAgoLoadingLayout.visibility = View.GONE
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    two_days_ago_image_view.load(myUrl) {
                        lifecycle(this@TwoDaysAgoFragment)
                        error(R.drawable.ic_error_foreground)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                    two_days_ago_text_explanation.text = description
                }
            }
            is PictureOfTheDayData.Loading -> {
                twoDaysAgoLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Error -> {
                twoDaysAgoLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }

        }

    }
}