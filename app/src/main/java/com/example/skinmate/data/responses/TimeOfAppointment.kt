package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class TimeOfAppointment(
    @SerializedName("time")
    val time: List<String>
)