package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class ResponseInformationXX(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data
)