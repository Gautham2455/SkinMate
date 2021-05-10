package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class doctorListResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<ResponseInformationXXX>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)