package com.example.mynasaapplication.ui.main.fragments

import android.icu.text.DateFormat.getDateInstance
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.mynasaapplication.R
import com.example.mynasaapplication.model.POD.PictureOfTheDayData
import com.example.mynasaapplication.ui.main.viewModel.TwoDaysAgoViewModel
import kotlinx.android.synthetic.main.picture_of_yesterday_fragment.*
import kotlinx.android.synthetic.main.two_days_ago_fragment.*
import java.util.*

class TwoDaysAgoFragment : Fragment() {
    private lateinit var datePicker: DatePicker
    private lateinit var searchDate : String
    private lateinit var dateBtn : Button
    private lateinit var textDate : TextView

    companion object{
        fun newInstance() = TwoDaysAgoFragment()
    }

    private val viewModel: TwoDaysAgoViewModel by lazy {
        ViewModelProvider(this).get(TwoDaysAgoViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.two_days_ago_fragment, container, false)
        dateBtn = view.findViewById(R.id.btn_date)
        textDate = view.findViewById(R.id.text_date)
        datePicker = view.findViewById(R.id.datePicker)
        initDatePicker()
        dateBtn.setOnClickListener {
            viewModel.getLiveData(searchDate).observe(viewLifecycleOwner, {renderData(it)})
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel.getLiveData(searchDate).observe(viewLifecycleOwner, {renderData(it)})

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initDatePicker(){
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) { view, year, monthOfYear, dayOfMonth ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val newDate = Date(datePicker.year - 1900, datePicker.month , datePicker.dayOfMonth)
            searchDate = dateFormat.format(newDate)
        }
    }

    fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val myUrl= serverResponseData.url
                val description = serverResponseData.explanation
                if (myUrl.isNullOrEmpty()) {
                    Toast.makeText(context, "Ссылка пустая", Toast.LENGTH_SHORT).show()
                } else {
                    twoDaysAgoLoadingLayout.visibility = View.GONE
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    two_days_ago_image_view.load(myUrl) {
                        lifecycle(this@TwoDaysAgoFragment)
                        error(R.drawable.ic_error_foreground)
                        placeholder(R.drawable.ic_no_photo_foreground)
                    }
                    two_days_ago_text_explanation.text = description
                }
            }
            is PictureOfTheDayData.Loading -> {
                twoDaysAgoLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayData.Error -> {
                twoDaysAgoLoadingLayout.visibility = View.VISIBLE
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }

        }

    }
}