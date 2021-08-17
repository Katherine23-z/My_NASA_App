package com.example.mynasaapplication.ui.main.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.EarthPhoto.PhotoOfEarthData
import com.example.mynasaapplication.ui.main.viewModel.PhotoOfEarthViewModel

class EarthFragment : Fragment() {
    private lateinit var earthImg : ImageView
    private lateinit var earthLoadingLayout: FrameLayout

    val viewModel : PhotoOfEarthViewModel by lazy {
        ViewModelProvider(this).get(PhotoOfEarthViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.earth_fragment, container, false)
        earthImg = view.findViewById(R.id.earth_image_view)
        earthLoadingLayout = view.findViewById(R.id.earth_fragment_loading_layout)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    private fun renderData(data : PhotoOfEarthData){
        when (data) {
            is PhotoOfEarthData.Success -> {
                val serverResponseData = data.earthServerResponse
                val url = serverResponseData.url
                if(url.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Null", Toast.LENGTH_SHORT).show()
                } else {
                    earthLoadingLayout.visibility = View.GONE
                    earthImg.load(url){
                        lifecycle(this@EarthFragment)
                        error(R.drawable.ic_error_foreground)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                }
            }
            is PhotoOfEarthData.Loading -> {
                earthLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is PhotoOfEarthData.Error -> {
                earthLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}