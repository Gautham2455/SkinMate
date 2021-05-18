package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class notificationResponse (
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation:ArrayList<String>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)