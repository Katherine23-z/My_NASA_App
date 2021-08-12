package com.example.mynasaapplication.model.POD

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData : PODServerResponseData) : PictureOfTheDayData()
    data class Loading(val progress : Int?) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
}
