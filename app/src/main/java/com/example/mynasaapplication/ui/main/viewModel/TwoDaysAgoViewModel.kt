package com.example.mynasaapplication.ui.main.viewModel

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynasaapplication.BuildConfig
import com.example.mynasaapplication.model.PODRetrofitImpl
import com.example.mynasaapplication.model.PODServerResponseData
import com.example.mynasaapplication.model.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TwoDaysAgoViewModel (private val liveDataToObserve : MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
                           private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLiveData() : LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataToObserve
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getDate() : String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -2)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date.time)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendServerRequest() {
        liveDataToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(getDate(), apiKey).enqueue(object :
                    Callback<PODServerResponseData> {
                override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value =
                                PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                    PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value =
                                    PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }

}