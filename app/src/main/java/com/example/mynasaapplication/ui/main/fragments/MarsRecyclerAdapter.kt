package com.example.mynasaapplication.ui.main.fragments

import android.graphics.Rect
import android.transition.Explode
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionSet
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.PictureOfMars.MarsPhoto
import com.example.mynasaapplication.model.PictureOfMars.Photo
import com.example.mynasaapplication.model.PictureOfMars.getMarsPhotos
import com.example.mynasaapplication.ui.main.api.MarsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MarsRecyclerAdapter(private val marsPhotos: ArrayList<Photo>) : RecyclerView.Adapter<MarsRecyclerAdapter.MarsViewHolder>(), CoroutineScope by MainScope() {


    inner class MarsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            fun bind(marsPhoto: Photo) {
                itemView.apply {
                    launch(Dispatchers.IO) {
                        findViewById<ImageView>(R.id.mars_item_view).load(marsPhoto.imgSrc)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent, false)
        return MarsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.bind(marsPhotos[position])
    }

    override fun getItemCount(): Int {
        return marsPhotos.size
    }

}