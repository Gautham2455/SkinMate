package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class TimeOfAppointmentX(
    @SerializedName("time")
    val time: List<String>
)