package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class customerViewResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: ResponseInformationXX,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)