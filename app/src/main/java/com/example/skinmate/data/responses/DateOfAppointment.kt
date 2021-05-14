package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class DateOfAppointment(
    @SerializedName("date")
    val date: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_type")
    val timezoneType: Int
)