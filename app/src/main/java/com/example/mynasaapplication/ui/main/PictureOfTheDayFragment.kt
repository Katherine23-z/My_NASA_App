package com.example.mynasaapplication.ui.main

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import coil.api.load
import com.example.mynasaapplication.MainActivity
import com.example.mynasaapplication.R
import com.example.mynasaapplication.databinding.PictureOfTheDayFragmentBinding
import com.example.mynasaapplication.model.PictureOfTheDayData
import com.example.mynasaapplication.ui.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class PictureOfTheDayFragment : Fragment() {

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }
    private lateinit var navigation: Navigation
    private lateinit var main: LinearLayout
    private lateinit var imageView: ImageView
    private lateinit var explanation :TextView
    private lateinit var mainFragmentLoadingLayout: FrameLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.picture_of_the_day_fragment, container, false)
        val activity: MainActivity = context as MainActivity
        navigation = activity.getNavigation()
        main = view.findViewById(R.id.main)
        imageView = view.findViewById(R.id.image_view)
        explanation = view.findViewById(R.id.bottom_sheet_description)
        mainFragmentLoadingLayout = view.findViewById(R.id.mainFragmentLoadingLayout)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
        view.findViewById<TextInputLayout>(R.id.input_layout).setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${view.findViewById<TextInputEditText>(R.id.input_edit_text).text.toString()}")
            })
        }
    }

    fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val myUrl= serverResponseData.url
                val description = serverResponseData.explanation
                if (myUrl.isNullOrEmpty()) {
                    Snackbar.make(main, "Ссылка пустая", Snackbar.LENGTH_LONG).show()
                } else {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    Snackbar.make(main, "Sucess", Snackbar.LENGTH_LONG).show()
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    imageView.load(myUrl) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_error_foreground)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                    showDescription(explanation, description)
                }
            }
            is PictureOfTheDayData.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
                Snackbar.make(main, "Loading", Snackbar.LENGTH_LONG).show()
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Error -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
                Snackbar.make(main, "Error", Snackbar.LENGTH_LONG).show()
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }

        }

    }

    fun showDescription(textView: TextView, text: String?) {
        textView.text = text
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addToFav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.action_settings -> Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            R.id.action_clear -> Toast.makeText(context, "Clear", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(requireFragmentManager(),"TAG")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        val bottomAppBar = view.findViewById<BottomAppBar>(R.id.bottom_app_bar)
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            if(isMain){
                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }
            else {
                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
            isMain = isMain.not()
        }
    }


}