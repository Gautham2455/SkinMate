package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class ResponseInformationX(
    @SerializedName("familyProfileId")
    val familyProfileId: String,
    @SerializedName("fristName")
    val fristName: String
)