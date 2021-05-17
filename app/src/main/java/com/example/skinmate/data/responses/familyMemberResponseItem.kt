package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class familyMemberResponseItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: ResponseInformationX,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)