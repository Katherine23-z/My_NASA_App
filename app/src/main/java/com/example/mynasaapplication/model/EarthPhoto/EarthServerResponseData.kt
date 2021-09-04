package com.example.mynasaapplication.model.EarthPhoto

import com.google.gson.annotations.SerializedName

data class EarthServerResponseData (
    @SerializedName("url") val url : String?
        )