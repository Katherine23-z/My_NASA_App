
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
import com.example.mynasaapplication.ui.main.viewModel.PictureOfYesterdayViewModel
import kotlinx.android.synthetic.main.picture_of_yesterday_fragment.*

class PictureOfYesterdayFragment : Fragment() {

    companion object{
        fun newInstance() = PictureOfYesterdayFragment()
    }

    private val viewModel: PictureOfYesterdayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfYesterdayViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.picture_of_yesterday_fragment, container, false)
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
                    yesterdayLoadingLayout.visibility = View.GONE
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    yesterday_image_view.load(myUrl) {
                        lifecycle(this@PictureOfYesterdayFragment)
                        error(R.drawable.ic_error_foreground)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                    yesterday_text_explanation.text = description
                }
            }
            is PictureOfTheDayData.Loading -> {
                yesterdayLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Error -> {
                yesterdayLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }

        }

    }
}