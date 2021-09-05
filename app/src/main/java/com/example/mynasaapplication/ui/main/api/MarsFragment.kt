package com.example.mynasaapplication.ui.main.api

import android.graphics.Rect
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
import androidx.transition.*
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.PictureOfMars.Photo
import com.example.mynasaapplication.model.PictureOfMars.PhotoOfMarsData
import com.example.mynasaapplication.ui.main.fragments.MarsRecyclerAdapter
import com.example.mynasaapplication.ui.main.viewModel.PhotoOfMarsViewModel


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
        val view = inflater.inflate(R.layout.mars_recycler_layout, container, false)
       marsLoadingLayout = view.findViewById(R.id.mars_fragment_loading_layout)
        marsRecycler = view.findViewById(R.id.mars_recycler)
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
                   marsLoadingLayout.visibility = View.GONE
                    marsRecycler.adapter = MarsAnimationTestAdapter(photos)
                }
            }
            is PhotoOfMarsData.Loading -> {
                   marsLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is PhotoOfMarsData.Error -> {
                   marsLoadingLayout.visibility = View.VISIBLE
                   Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
}

    private fun explode(clickedView : View){
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback(){
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.duration = 2000
        explode.excludeTarget(clickedView, true)
        val set = TransitionSet()
                .addTransition(explode)
                .addTransition(Fade().addTarget(clickedView))
                .addListener(object : TransitionListenerAdapter(){
                    override fun onTransitionStart(transition: Transition) {
                        Toast.makeText(requireContext(), "Animation Start", Toast.LENGTH_SHORT).show()
                    }
                })
        TransitionManager.beginDelayedTransition(marsRecycler, set)
        marsRecycler.adapter = null
    }


    inner class MarsAnimationTestAdapter(private val marsPhotos: ArrayList<Photo>) : RecyclerView.Adapter<MarsAnimationTestAdapter.AnimViewHolder>() {

        inner class AnimViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(marsPhoto: Photo) {
                itemView.apply {
                    launch(Dispatchers.IO) {
                        findViewById<ImageView>(R.id.mars_item_view).load(marsPhoto.imgSrc)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimViewHolder {
           return AnimViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent, false) as View)

        }

        override fun onBindViewHolder(holder: AnimViewHolder, position: Int) {
            holder.bind(marsPhotos[position])
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
           return 18
        }
    }
}