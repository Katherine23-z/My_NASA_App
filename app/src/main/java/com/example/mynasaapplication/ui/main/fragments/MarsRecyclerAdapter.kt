package com.example.mynasaapplication.ui.main.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.PictureOfMars.MarsPhoto
import com.example.mynasaapplication.model.PictureOfMars.Photo
import com.example.mynasaapplication.model.PictureOfMars.getMarsPhotos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MarsRecyclerAdapter(val marsPhotos: ArrayList<Photo>) : RecyclerView.Adapter<MarsRecyclerAdapter.MarsViewHolder>(), CoroutineScope by MainScope() {


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