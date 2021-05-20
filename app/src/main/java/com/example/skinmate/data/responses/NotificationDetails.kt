package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class NotificationDetails(
    @SerializedName("Date")
    val Date: String,
    @SerializedName("Time")
    val Time: String,
    @SerializedName("Status")
    val Status: String,
    @SerializedName("FirstName")
    val FirstName: String,
    @SerializedName("Message")
    val Message: String
)

