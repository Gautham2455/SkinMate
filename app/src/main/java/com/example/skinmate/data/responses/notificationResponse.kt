package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class notificationResponse (
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation:List<NotificationDetails>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)