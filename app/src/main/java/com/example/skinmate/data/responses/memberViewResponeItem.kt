package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class memberViewResponeItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<ResponseInformationXY>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)