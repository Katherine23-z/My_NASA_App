package com.example.mynasaapplication.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey : String) : Call<PODServerResponseData>
}