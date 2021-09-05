package com.example.mynasaapplication.ui.main.api.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.mynasaapplication.model.NoteData

interface OnListItemClickListener {
    fun onItemClick (noteData: NoteData)
}

interface ItemTouchHelperAdapter{
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)

}

interface ItemTouchHelperViewHolder {

    fun onItemSelected()

    fun onItemClear()
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}
