package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class MainServiceResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<ResponseInformationXXXX>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)