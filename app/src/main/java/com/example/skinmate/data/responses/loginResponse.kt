package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class loginResponse(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("responseInformation")
    val responseInformation: String,
    @SerializedName("responseMessage")
    val responseMessage: Boolean,
    @SerializedName("token")
    val token: String
)