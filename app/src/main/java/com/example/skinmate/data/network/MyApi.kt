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
        @Field("id") id : String,
        @Field("phoneNumber") phoneNumber: String
    ) : Call<List<duplicateUserResponse>>

    @POST("customer/login")
    fun userLogin(
        @Body requestBody: RequestBody
    ) : Call<loginResponse>

    @FormUrlEncoded
    @POST("customer/mobile-otp-verify")
    fun verifyMobleOtp(
        @Field("otp") otp : Int
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
        @Field("customerId") customerId : String,
        @Field("oldPassword")  oldPassword: String,
        @Field("newPassword")  newPassword: String
    ) : Call<List<passwordChangeResponse>>

    @FormUrlEncoded
    @POST("customer/update-password")
    fun updatePassword(
        @Field("email") customerId : String,
        @Field("password")  oldPassword: String,
    ) : Call<List<updatePasswordResponse>>

    @GET("customer/family-member/list/")
    fun familyList(
        @Query("customerId") customerId:String
    ) :Call<familyMemberList>

    @FormUrlEncoded
    @POST("customer/family-member/delete")
    fun deleteFamilyMember(
        @Field("familyProfileId") familyProfileId:String
    ): Call<List<generalResponse>>

    @GET("subtype-of-service/list")
    fun getSubService(
        @Query("serviceId") serviceId:String
    ):Call<subServiceResponse>

    @GET("customer/family-member/list/")
    fun famllyMember(
        @Query("familyProfileId") familyProfileId:String
    ):Call<familyMemberResponse>

    @GET("customer/view")
    fun customerDetails(
        @Query("id") id :String
    ):Call<customerViewResponse>
}