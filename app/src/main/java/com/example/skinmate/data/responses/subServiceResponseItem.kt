package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class subServiceResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: ResponseInformation,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)