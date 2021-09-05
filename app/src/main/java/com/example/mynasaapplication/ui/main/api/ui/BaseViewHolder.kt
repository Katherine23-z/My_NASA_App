package com.example.mynasaapplication.ui.main.api.ui

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mynasaapplication.model.NoteData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {
    abstract fun bind(noteData: Pair<NoteData, Boolean>)

    override fun onItemSelected() {
       itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(Color.WHITE)
    }
}