package com.example.mynasaapplication.model

import android.os.Parcel
import android.os.Parcelable
import com.example.mynasaapplication.R

data class NoteData (val someText: String? = "", val someDescription: String? = "", val image : Int = R.drawable.mars_photo) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(someText)
        parcel.writeString(someDescription)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteData> {
        override fun createFromParcel(parcel: Parcel): NoteData {
            return NoteData(parcel)
        }

        override fun newArray(size: Int): Array<NoteData?> {
            return arrayOfNulls(size)
        }
    }
}