package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class Dob(
    @SerializedName("date")
    val date: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_type")
    val timezoneType: Int
)