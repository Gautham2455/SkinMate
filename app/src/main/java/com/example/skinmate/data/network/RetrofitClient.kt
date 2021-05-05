package com.example.skinmate.data.network

import com.example.skinmate.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient  {

    val retrofitClient: Retrofit.Builder by lazy{

        val okhttpClient = OkHttpClient.Builder()

        Retrofit.Builder()
            .baseUrl("http://65.0.55.180/skinmate/v1.0/customer/")
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: MyApi by lazy {
        retrofitClient
            .build()
            .create(MyApi::class.java)
    }
}