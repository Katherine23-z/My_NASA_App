package com.example.mynasaapplication.model.EarthPhoto


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoOfEarthAPI {
    @GET("/planetary/earth/assets")
    fun getPhotoOfEarth(@Query(value = "lon") lon : Double, @Query(value = "lat") lat : Double, @Query("api_key") apiKey : String) : Call<EarthServerResponseData>
}