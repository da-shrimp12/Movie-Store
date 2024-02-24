package com.example.moviestore.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CastCrew(

    @SerializedName("adult") var adult: Boolean?,
    @SerializedName("gender") var gender: Int?,
    @SerializedName("id") var id: Int?,
    @SerializedName("known_for_department") var knownForDepartment: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("original_name") var originalName: String?,
    @SerializedName("popularity") var popularity: Double?,
    @SerializedName("profile_path") var profilePath: String?,
    @SerializedName("cast_id") var castId: Int?,
    @SerializedName("character") var character: String?,
    @SerializedName("credit_id") var creditId: String?,
    @SerializedName("order") var order: Int?,
    @SerializedName("department") var department: String?,
    @SerializedName("job") var job: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(adult)
        parcel.writeValue(gender)
        parcel.writeValue(id)
        parcel.writeString(knownForDepartment)
        parcel.writeString(name)
        parcel.writeString(originalName)
        parcel.writeValue(popularity)
        parcel.writeString(profilePath)
        parcel.writeValue(castId)
        parcel.writeString(character)
        parcel.writeString(creditId)
        parcel.writeValue(order)
        parcel.writeString(department)
        parcel.writeString(job)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CastCrew> {
        override fun createFromParcel(parcel: Parcel): CastCrew {
            return CastCrew(parcel)
        }

        override fun newArray(size: Int): Array<CastCrew?> {
            return arrayOfNulls(size)
        }
    }
}