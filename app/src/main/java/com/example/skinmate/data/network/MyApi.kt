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
        @Field("emeregencyContactName") emeregencyContactName : String,
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