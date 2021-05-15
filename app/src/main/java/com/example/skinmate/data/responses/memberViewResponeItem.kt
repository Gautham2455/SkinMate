package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class memberViewResponeItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseMessage")
    val responseMessage: Boolean,
    @SerializedName("responseInformation")
    val responseInformation: List<ResponseInformationXY>

)