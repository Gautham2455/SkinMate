package com.example.skinmate.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST

interface MyApi {

    @POST("registration")
    suspend fun userRegistration(
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
    )

    @POST("duplicate-checker")
    fun checkDuplicateUser(
        @Field("email") email : String,
        @Field("phoneNumber") phoneNumber: String
    )

    @POST("login")
    suspend fun userLogin(
        @Field("id") email : String,
        @Field("password") password: String
    )

    @POST("mobile-otp-verify")
    suspend fun verifyMobleOtp(
        @Field("otp") otp : String
    )

    @POST("registration-send-otp-to-email")
    suspend fun registerEmail(
        @Field("email") email : String
    )

    @POST("email-otp-verify")
    suspend fun verifyEmailOtp(
        @Field("email") email : String,
        @Field("otp") otp : String
    )


    companion object{
        operator fun invoke() : MyApi {

            return Retrofit.Builder()
                .baseUrl("http://65.0.55.180/skinmate/v1.0/customer/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}