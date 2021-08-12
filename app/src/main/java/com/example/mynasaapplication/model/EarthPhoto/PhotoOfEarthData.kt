package com.example.mynasaapplication.model.EarthPhoto

sealed class PhotoOfEarthData {
    data class Success(val earthServerResponse : EarthServerResponseData) : PhotoOfEarthData()
    data class Loading(val progress : Int?) : PhotoOfEarthData()
    data class Error(val e : Throwable) : PhotoOfEarthData()
}