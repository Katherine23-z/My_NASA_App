package com.example.mynasaapplication.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynasaapplication.BuildConfig
import com.example.mynasaapplication.model.EarthPhoto.EarthRetrofitImpl
import com.example.mynasaapplication.model.EarthPhoto.EarthServerResponseData
import com.example.mynasaapplication.model.EarthPhoto.PhotoOfEarthData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoOfEarthViewModel(private val liveDataToObserve : MutableLiveData<PhotoOfEarthData> = MutableLiveData(),
                            private val retrofitImpl: EarthRetrofitImpl = EarthRetrofitImpl()) :ViewModel() {

          fun getLiveData() : LiveData<PhotoOfEarthData>{
              sendServerRequest()
              return liveDataToObserve
          }

    private fun sendServerRequest() {
        liveDataToObserve.value = PhotoOfEarthData.Loading(null)
        val apiKey = BuildConfig.NASA_API_KEY
        if(apiKey.isBlank()) {
            PhotoOfEarthData.Error(Throwable("You Need ApiKey"))
        } else {
            retrofitImpl.getEarthRetrofitImpl().getPhotoOfEarth(53.346575, 50.201963, apiKey).enqueue(object : Callback<EarthServerResponseData>{
                override fun onResponse(
                    call: Call<EarthServerResponseData>,
                    response: Response<EarthServerResponseData>
                ) {
                   if (response.isSuccessful && response.body() !=null){
                       liveDataToObserve.value = PhotoOfEarthData.Success(response.body()!!)
                   }
                    else {
                        val message = response.message()
                       if (message.isNullOrEmpty()) {
                           liveDataToObserve.value = PhotoOfEarthData.Error(Throwable("Unidentified error"))
                       }
                       else {
                           liveDataToObserve.value = PhotoOfEarthData.Error(Throwable(message))
                       }
                   }
                }

                override fun onFailure(call: Call<EarthServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = PhotoOfEarthData.Error(t)
                }

            })
        }
    }


}