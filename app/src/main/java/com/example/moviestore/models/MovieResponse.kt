package com.example.moviestore.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results")
    var movies: List<Movie>

) : Parcelable {
    constructor() : this(mutableListOf())
}