package com.example.mynasaapplication.model.PictureOfMars

import com.google.gson.annotations.SerializedName

 class MarsServerResponseData(

      @SerializedName("photos") val photos: ArrayList<Photo>?
)