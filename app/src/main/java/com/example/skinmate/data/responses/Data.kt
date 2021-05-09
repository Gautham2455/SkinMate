package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String,
    @SerializedName("bloodGroup")
    val bloodGroup: String,
    @SerializedName("dob")
    val dob: Dob,
    @SerializedName("email")
    val email: String,
    @SerializedName("emeregencyNumber")
    val emeregencyNumber: String,
    @SerializedName("emergencyContactName")
    val emergencyContactName: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("insuranceInformation")
    val insuranceInformation: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)