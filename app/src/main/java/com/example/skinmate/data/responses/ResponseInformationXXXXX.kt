package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class ResponseInformationXXXXX(
    @SerializedName("insuranceId")
    val insuranceId: Int,
    @SerializedName("insuranceInformation")
    val insuranceInformation: String
)