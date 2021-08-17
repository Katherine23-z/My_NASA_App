package com.example.mynasaapplication.model.PictureOfMars

sealed class PhotoOfMarsData {
    data class Success(val serverResponseData: MarsServerResponseData) : PhotoOfMarsData()
    data class Loading(val progress : Int?) : PhotoOfMarsData()
    data class Error(val error : Throwable) : PhotoOfMarsData()
}