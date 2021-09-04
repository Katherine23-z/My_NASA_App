package com.example.mynasaapplication.ui.main.api.ui

import android.icu.text.CaseMap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynasaapplication.MainActivity
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.NoteData
import com.example.mynasaapplication.ui.Navigation
import kotlinx.android.synthetic.main.note_recycler_fragment.*
import kotlinx.android.synthetic.main.note_recycler_image_item.*

class NoteFragment : Fragment() {
    private lateinit var navigation: Navigation
    private lateinit var adapter : NoteRecyclerAdapter
    lateinit var itemTouchHelper : ItemTouchHelper
    private val notes = arrayListOf<Pair<NoteData, Boolean>>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.note_recycler_fragment, container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        notes.add(0, Pair(NoteData("Header"), false))
        val activity = context as MainActivity
        navigation = activity.getNavigation()


            adapter = NoteRecyclerAdapter(notes, object : OnListItemClickListener{
            override fun onItemClick(noteData: NoteData) {
                Toast.makeText(requireContext(), noteData.someText, Toast.LENGTH_SHORT).show()
            }
        }, object : OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }

        })
        note_recycler.adapter = adapter
        note_recycler.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        note_recycler_fab.setOnClickListener {
            text_input_layout.visibility = View.VISIBLE
        }
        ok_btn.setOnClickListener {
            notes.add(Pair(NoteData(title_edit.text.toString(), title_content.text.toString()), false))
            adapter.notifyItemInserted(notes.size - 1)
            title_edit.text = null
            title_content.text = null
            text_input_layout.visibility = View.GONE
        }

        itemTouchHelper =ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(note_recycler)

    }


}