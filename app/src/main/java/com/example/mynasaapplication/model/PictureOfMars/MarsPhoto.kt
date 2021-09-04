package com.example.mynasaapplication.model.PictureOfMars

data class MarsPhoto(val marsImageUrl : String = "")

fun getMarsPhotos() = mutableListOf<MarsPhoto>()