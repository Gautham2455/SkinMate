package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class FamilyMemberDetails(
    @SerializedName("address")
    val address: String,
    @SerializedName("bloodGroup")
    val bloodGroup: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("emeregencyContactName")
    val emeregencyContactName: String,
    @SerializedName("emeregencyNumber")
    val emeregencyNumber: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("insuranceInformation")
    val insuranceInformation: String,
    @SerializedName("lastName")
    val lastName: String
)