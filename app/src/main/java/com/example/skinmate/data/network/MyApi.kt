package com.example.skinmate.data.network

import com.example.skinmate.data.responses.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface   MyApi {

    @FormUrlEncoded
    @POST("registration")
    fun userRegistration(
        @Field("phoneNumber") phoneNumber: Int,
        @Field("email") email : String,
        @Field("firstName") firstName : String,
        @Field("lastName") lastName : String,
        @Field("gender") gender : String,
        @Field("dob") dob : String,
        @Field("bloodGroup") bloodGroup : String,
        @Field("loginType") loginType : String,
        @Field("password") password : String,
        @Field("address") address : String,
        @Field("emeregencyNumber") emeregencyNumber : Int,
        @Field("insuranceInformation") insuranceInformation : String,
        @Field("emeregencyContactName") emeregencyContactName : String
    ) : Call<List<registerUserResponse>>


    @FormUrlEncoded
    @POST("duplicate-checker")
    fun checkDuplicateUser(
        @Field("id") id : String,
        @Field("phoneNumber") phoneNumber: Int
    ) : Call<List<duplicateUserResponse>>

    @POST("login")
    fun userLogin(
        @Body requestBody: RequestBody
    ) : Call<loginResponse>

    @FormUrlEncoded
    @POST("mobile-otp-verify")
    fun verifyMobleOtp(
        @Field("otp") otp : Int
    ) : Call<List<OtpResponse>>

    @FormUrlEncoded
    @POST("registration-send-otp-to-email")
    fun registerEmail(
        @Field("email") email : String
    ) : Call<List<OtpResponse>>

    @FormUrlEncoded
    @POST("email-otp-verify")
    fun verifyEmailOtp(
        @Field("email") email : String,
        @Field("otp") otp : Int
    ) : Call<List<OtpResponse>>

    @FormUrlEncoded
    @POST("change-password")
    fun changePassword(
        @Field("customerId") customerId : Int,
        @Field("oldPassword")  oldPassword: String,
        @Field("newPassword")  newPassword: String
    ) : Call<List<passwordChangeResponse>>

    @FormUrlEncoded
    @POST("update-password")
    fun updatePassword(
        @Field("email") customerId : String,
        @Field("Password")  oldPassword: String,
    ) : Call<List<updatePasswordResponse>>
}