package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class duplicateUserResponse(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: String,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)