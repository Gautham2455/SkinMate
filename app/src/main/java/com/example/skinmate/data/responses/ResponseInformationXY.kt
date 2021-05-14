package com.example.skinmate.data.responses

import com.google.gson.annotations.SerializedName

data class ResponseInformationXY (

    @SerializedName("familyProfileId")
    val familyProfileId : Int,
    @SerializedName("customerId")
    val customerId: String,
    @SerializedName("relationshipId")
    val relationshipId: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("bloodGroup")
    val bloodGroup: String,
    @SerializedName("emeregencyContactName")
    val emeregencyContactName: String,
    @SerializedName("emeregencyNumber")
    val emeregencyNumber: String,
    @SerializedName("insuranceInformation")
    val insuranceInformation: String

)