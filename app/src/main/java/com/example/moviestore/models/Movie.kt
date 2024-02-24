package com.example.moviestore.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("vote_average")
    val vote: String?,

    @SerializedName("release_date")
    val release: String?,

    var isFavorite: Boolean

) : Parcelable {
    constructor() : this("", "", "", "", "", "", false)
    var typeDisplay = 2
    companion object {
        const val TYPE_GRID = 1
        const val TYPE_LIST = 2
    }
}

