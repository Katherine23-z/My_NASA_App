package com.example.mynasaapplication.model.PictureOfMars

import com.example.mynasaapplication.model.POD.PODServerResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoOfMarsAPI {

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getPhotoOfMars(@Query(value = "sol") sol : Int, @Query("api_key") apiKey : String) : Call<MarsServerResponseData>
}