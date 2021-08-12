package com.example.mynasaapplication.model.POD

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query(value = "date") date : String, @Query("api_key") apiKey : String) : Call<PODServerResponseData>
}