package com.example.mynasaapplication.ui.main.viewModel

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynasaapplication.BuildConfig
import com.example.mynasaapplication.model.PictureOfMars.MarsRetrofitImpl
import com.example.mynasaapplication.model.PictureOfMars.MarsServerResponseData
import com.example.mynasaapplication.model.PictureOfMars.PhotoOfMarsData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.util.*

class PhotoOfMarsViewModel(private val liveDataToObserve : MutableLiveData<PhotoOfMarsData> = MutableLiveData(),
                           private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()) : ViewModel(), CoroutineScope by MainScope() {

     @RequiresApi(Build.VERSION_CODES.N)
     fun getLiveData() : LiveData<PhotoOfMarsData> {
         sendServerRequest()
         return liveDataToObserve
     }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sendServerRequest() {
        liveDataToObserve.value = PhotoOfMarsData.Loading(null)
        val apiKey : String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PhotoOfMarsData.Error(Throwable("You need ApiKey"))
        } else {
             retrofitImpl.getMarsRetrofitImpl().getPhotoOfMars(1000, apiKey).enqueue(object : Callback<MarsServerResponseData>{
                override fun onResponse(call: Call<MarsServerResponseData>,
                                        response: Response<MarsServerResponseData>) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value =
                                PhotoOfMarsData.Success(response.body()!!)
                    } else  {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value =
                                    PhotoOfMarsData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value =
                                    PhotoOfMarsData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = PhotoOfMarsData.Error(t)
                }

            })
        }
    }
}