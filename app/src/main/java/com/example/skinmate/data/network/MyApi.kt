package com.example.skinmate.data.network

import com.example.skinmate.data.responses.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface   MyApi {

    @POST("customer/registration")
    fun userRegistration(
        @Body requestBody: RequestBody
    ) : Call<List<registerUserResponse>>


    @FormUrlEncoded
    @POST("customer/duplicate-checker")
    fun checkDuplicateUser(
        @Field("email") email : String,
        @Field("phoneNumber") phoneNumber: String
    ) : Call<List<duplicateUserResponse>>

    @POST("customer/login")
    fun userLogin(
        @Body requestBody: RequestBody
    ) : Call<loginResponse>

    @FormUrlEncoded
    @POST("customer/mobile-otp-verify")
    fun verifyMobleOtp(
        @Field("otp") otp : Int,
        @Field("mobileNumber") mobileNumber :String
    ) : Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("customer/registration-send-otp-to-email")
    fun registerEmail(
        @Field("email") email : String
    ) : Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("customer/email-otp-verify")
    fun verifyEmailOtp(
        @Field("email") email : String,
        @Field("otp") otp : Int
    ) : Call<List<generalResponse>>


    @FormUrlEncoded
    @POST("customer/change-password")
    fun changePassword(
        @Header("Authorization") token :String,
        @Field("customerId") customerId : String,
        @Field("oldPassword")  oldPassword: String,
        @Field("newPassword")  newPassword: String
    ) : Call<List<passwordChangeResponse>>

    @FormUrlEncoded
    @POST("customer/update-password")
    fun updatePassword(
        @Field("email") email : String,
        @Field("password")  oldPassword: String,
    ) : Call<List<updatePasswordResponse>>


    @GET("customer/family-member/list/{customerId}")
    fun familyList(
        @Header("Authorization") token :String,
        @Path("customerId") customerId:String
    ) :Call<List<familyMemberListItem>>

//    @FormUrlEncoded
//    @POST("customer/family-member/delete")
//    fun deleteFamilyMember(
//        @Header("Authorization") token :String,
//        @Field("familyProfileId") familyProfileId:String
//    ): Call<List<generalResponse>>


    @GET("subtype-of-service/list/{serviceId}")
    fun getSubService(
        @Header("Authorization") token :String,
        @Path("serviceId") serviceId:String
    ):Call<subServiceResponse>

    @GET("customer/family-member/list/{familyProfileId}")
    fun famllyMember(
        @Header("Authorization") token :String,
        @Path("familyProfileId") familyProfileId:String
    ):Call<familyMemberResponse>

    @FormUrlEncoded
    @POST("customer/view")
    fun customerDetails(
        @Header("Authorization") token :String,
        @Field("customerId") customerId :String
    ):Call<customerViewResponse>

    @FormUrlEncoded
    @POST("doctors/list")
    fun doctorList(
        @Header("Authorization") token :String,
        @Field("serviceId") serviceId:String
    ):Call<doctorListResponse>

    @FormUrlEncoded
    @POST("customer/family/add")
    fun addFamilyMember(
        @Header("Authorization") token :String,
        @Field("customerId") customerId:String,
        @Field("relationshipId") relationshipId:String,
        @Field("firstName") firstName:String,
        @Field("lastName") lastName:String,
        @Field("gender") gender:String,
        @Field("dob")dob:String,
        @Field("bloodGroup") bloodGroup:String,
        @Field("address") address:String,
        @Field("insuranceInformation") insuranceInformation:String,
        @Field("emeregencyContactName") emeregencyContactName:String,
        @Field("emeregencyNumber") emeregencyNumber:String
    ):Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("customer/edit")
    fun customerEdit(
        @Header("Authorization") token :String,
        @Field("customerId") customerId:String,
        @Field("address") address:String,
        @Field("email") email:String,
        @Field("insuranceInformation") insuranceInformation:String,
        @Field("emeregencyContactName") emeregencyContactName:String,
        @Field("emeregencyNumber") emeregencyNumber:String
    ):Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("doctor/timeslots")
    fun bookedAppointments(
        @Header("Authorization") token :String,
        @Field("doctorId") doctorId:String,
        @Field("date") date:String
    ):Call<bookedAppointmentResponse>

    @POST("customer/insurance/add")
    fun addInsurance(
        @Header("Authorization") token :String,
        @Body requestBody: RequestBody
    ):Call<List<generalResponse>>

    @POST("doctor/appointment/add")
    fun addAppointment(
        @Header("Authorization") token :String,
        @Body requestBody: RequestBody
    ):Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("customer/email-send-otp")
    fun registerId(
        @Header("Authorization") token:String,
        @Field("customerId") customerId: String
    ):Call<List<generalResponse>>

    @FormUrlEncoded
    @POST("customer/email-verify")
    fun emailOtpVerify(
        @Header("Authorization") token:String,
        @Field("customerId") customerId: String,
        @Field("otp") otp: String
    ):Call<List<generalResponse>>

    @GET("service/list")
    fun getMainSerice(
        @Header("Authorization") token:String
    ):Call<MainServiceResponse>

    @PUT("customer/family-member/edit/{familyProfileId}")
    fun putFamilyMemberEdit(
        @Header("Authorization") token: String,
        @Path("familyProfileId") familyProfileId:String,
        @Body requestBody : RequestBody
    ):Call<List<generalResponse>>


    @DELETE("customer/family-member/delete/{familyProfileId}")
    fun deleteMember(
        @Header("Authorization") token: String,
        @Path("familyProfileId") familyProfileId: String
    ):Call<List<generalResponse>>

    @GET("customer/family-profile/view/{familyProfileId}")
    fun viewFamilyMember(
        @Header("Authorization") token:String,
        @Path("familyProfileId") familyProfileId: String
    ) :Call<memberViewResponse>

}