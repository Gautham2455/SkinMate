package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class familyMemberListItem(
    @SerializedName("Code")
    val code: Int,
    @SerializedName("responseInformation")
    val responseInformation: List<FamilyMemberDetails>,
    @SerializedName("responseMessage")
    val responseMessage: Boolean
)