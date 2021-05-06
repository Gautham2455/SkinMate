package com.example.skinmate.data.network

import com.example.skinmate.data.responses.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST

interface MyApi {

    @POST("registration")
    fun userRegistration(
        @Field("id") id: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("email") email : String,
        @Field("firstName") firstName : String,
        @Field("lastName") lastName : String,
        @Field("gender") gender : String,
        @Field("dob") dob : String,
        @Field("bloodGroup") bloodGroup : String,
        @Field("loginType") loginType : String,
        @Field("password") password : String,
        @Field("address") address : String,
        @Field("emeregencyNumber") emeregencyNumber : String,
        @Field("insuranceInformation") insuranceInformation : String,
        @Field("emeregencyContactName") emeregencyContactName : String
    ) : Call<registerUserResponse>

    @POST("duplicate-checker")
    fun checkDuplicateUser(
        @Field("email") email : String,
        @Field("phoneNumber") phoneNumber: Int
    ) : Call<duplicateUserResponse>

    @POST("login")
    fun userLogin(
        @Field("id") email : String,
        @Field("password") password: String
    ) : Call<loginResponse>

    @POST("mobile-otp-verify")
    fun verifyMobleOtp(
        @Field("otp") otp : Int
    ) : Call<OtpResponse>

    @POST("registration-send-otp-to-email")
    fun registerEmail(
        @Field("email") email : String
    ) : Call<OtpResponse>

    @POST("email-otp-verify")
    fun verifyEmailOtp(
        @Field("email") email : String,
        @Field("otp") otp : Int
    ) : Call<OtpResponse>

    @POST("change-password")
    fun changePassword(
        @Field("customerId") customerId : Int,
        @Field("oldPassword")  oldPassword: String,
        @Field("newPassword")  newPassword: String
    ) : Call<passwordChangeResponse>

}