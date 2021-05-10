package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class bookAppointmentResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<String>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)