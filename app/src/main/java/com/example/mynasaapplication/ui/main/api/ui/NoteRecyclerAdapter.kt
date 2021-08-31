package com.example.mynasaapplication.ui.main.api.ui

import android.view.*
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.NoteData
import kotlinx.android.synthetic.main.note_recycler_header.view.*
import kotlinx.android.synthetic.main.note_recycler_image_item.view.*
import kotlinx.android.synthetic.main.note_recycler_text_item.view.*
import kotlinx.android.synthetic.main.note_recycler_text_item.view.note_title

class NoteRecyclerAdapter(private var noteData : MutableList<Pair<NoteData, Boolean>>,
                          private  var onListItemClickListener: OnListItemClickListener,
                          private val dragListener : OnStartDragListener  ) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter{

    companion object{
        private const val TYPE_TEXT = 0
        private const val TYPE_IMAGE = 1
        private const val TYPE_HEADER = 2
    }


    inner class TextNoteViewHolder(view : View) : BaseViewHolder(view){
        override fun bind(noteData: Pair<NoteData, Boolean>){
            if(layoutPosition != RecyclerView.NO_POSITION){
                itemView.note_title.text = noteData.first.someText
                itemView.short_description.text = noteData.first.someDescription
            }
        }
    }

    inner class ImageNoteViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(noteData: Pair<NoteData, Boolean>) {
            itemView.note_title1.text = noteData.first.someText
            itemView.note_description.text = noteData.first.someDescription
            itemView.note_image.setImageResource(noteData.first.image)
            itemView.note_image.setOnClickListener {
                onListItemClickListener.onItemClick(noteData.first)
            }
            itemView.drag_handle_image_view.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }

            itemView.remove_item_image_view.setOnClickListener {
                removeItem()
            }
            itemView.move_item_up.setOnClickListener {
                moveUp()
            }
            itemView.move_item_down.setOnClickListener {
                moveDown()
            }
            itemView.note_description.visibility = if(noteData.second)View.VISIBLE else View.GONE
            itemView.note_title1.setOnClickListener { toggleText() }

        }

        private fun toggleText(){
            noteData[layoutPosition] = noteData[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun addItem(){
            noteData.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem(){
            noteData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp(){
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                noteData.removeAt(currentPosition).apply {
                    noteData.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown(){
            layoutPosition.takeIf { it < noteData.size - 1 }?.also { currentPosition ->
                noteData.removeAt(currentPosition).apply {
                    noteData.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

    
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(noteData: Pair<NoteData, Boolean>) {
            itemView.header.text = noteData.first.someText
            itemView.setOnClickListener { onListItemClickListener.onItemClick(noteData.first) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TEXT -> TextNoteViewHolder(inflater.inflate(R.layout.note_recycler_text_item, parent, false) as View)
            TYPE_IMAGE -> ImageNoteViewHolder(inflater.inflate(R.layout.note_recycler_image_item, parent, false) as View)
            else -> HeaderViewHolder(inflater.inflate(R.layout.note_recycler_header, parent, false) as View)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
       holder.bind(noteData[position])
    }

    override fun getItemCount(): Int {
        return noteData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            noteData[position].first.someDescription.isNullOrBlank() -> TYPE_TEXT
            else -> TYPE_IMAGE
        }
    }

    fun addNote(){
        noteData.add(generateItem())
        notifyItemInserted(itemCount-1)
    }

    private fun generateItem() = Pair(NoteData("Note Example"), false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        noteData.removeAt(fromPosition).apply {
            noteData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        noteData.removeAt(position)
        notifyItemRemoved(position)
    }


}