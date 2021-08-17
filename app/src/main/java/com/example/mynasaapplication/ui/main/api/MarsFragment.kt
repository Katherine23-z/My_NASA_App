package com.example.mynasaapplication.ui.main.api

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.PictureOfMars.PhotoOfMarsData
import com.example.mynasaapplication.ui.main.fragments.MarsRecyclerAdapter
import com.example.mynasaapplication.ui.main.viewModel.PhotoOfMarsViewModel
import kotlinx.android.synthetic.main.mars_recycler_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class MarsFragment : Fragment(), CoroutineScope by MainScope(){
    private lateinit var marsRecycler: RecyclerView
    private lateinit var marsLoadingLayout : FrameLayout


    val viewModel: PhotoOfMarsViewModel by lazy{
        ViewModelProvider(this).get(PhotoOfMarsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.coordinator_layout_experiment, container, false)
        //marsLoadingLayout = view.findViewById(R.id.mars_fragment_loading_layout)
        marsRecycler = view.findViewById(R.id.mars_recycler_experiment)
        return view
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
                    renderData(it)

        })
    }

    private fun renderData(data: PhotoOfMarsData) {
        when (data) {
            is PhotoOfMarsData.Success -> {
                val serverResponseData = data.serverResponseData
                val photos = serverResponseData.photos
                if (photos.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Null", Toast.LENGTH_SHORT).show()
                }else {
                  // marsLoadingLayout.visibility = View.GONE
                    marsRecycler.adapter = MarsRecyclerAdapter(photos)
                }
            }
            is PhotoOfMarsData.Loading -> {
                //marsLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is PhotoOfMarsData.Error -> {
                //marsLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
}
}