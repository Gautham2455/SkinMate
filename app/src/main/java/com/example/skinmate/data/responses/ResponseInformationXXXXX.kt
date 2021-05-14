package com.example.skinmate.data.responses


import com.google.gson.annotations.SerializedName

data class ResponseInformationXXXXX(
    @SerializedName("appointmentId")
    val appointmentId: Int,
    @SerializedName("dateOfAppointment")
    val dateOfAppointment: DateOfAppointment,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("familyFirstName")
    val familyFirstName: String,
    @SerializedName("familyLastName")
    val familyLastName: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("serviceType")
    val serviceType: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("timeOfAppointment")
    val timeOfAppointment: TimeOfAppointment
)