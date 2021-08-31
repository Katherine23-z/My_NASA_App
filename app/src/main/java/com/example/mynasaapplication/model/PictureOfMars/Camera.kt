package com.example.mynasaapplication.model.PictureOfMars

import com.google.gson.annotations.SerializedName

data class Camera (
        @field:SerializedName("id") val id: Int?,
        @field:SerializedName("name") val name: String?,
        @field:SerializedName("rover_id") val roverId: Int?,
        @field:SerializedName("full_name") val fullName: Int?,

        )
