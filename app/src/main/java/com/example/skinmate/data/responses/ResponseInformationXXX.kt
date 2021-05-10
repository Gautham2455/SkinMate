package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class ResponseInformationXXX(
    @SerializedName("designation")
    val designation: String,
    @SerializedName("doctorId")
    val doctorId: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String
)