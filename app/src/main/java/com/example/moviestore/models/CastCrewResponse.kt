package com.example.moviestore.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CastCrewResponse(
    @SerializedName("cast")
    val castcrews: List<CastCrew>

) : Parcelable {
    constructor() : this(mutableListOf())
}