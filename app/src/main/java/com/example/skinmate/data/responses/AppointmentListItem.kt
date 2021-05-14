package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class AppointmentListItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<ResponseInformationXXXXXX>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)