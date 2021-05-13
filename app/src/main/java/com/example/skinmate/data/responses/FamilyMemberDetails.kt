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
    val emeregencyNumber: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("insuranceInformation")
    val insuranceInformation: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("customerId")
    val customerId:String,
    @SerializedName("familyProfileId")
    val familyProfileId:Int
)