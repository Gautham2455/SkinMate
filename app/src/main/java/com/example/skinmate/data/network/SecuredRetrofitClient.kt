package com.example.skinmate.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object SecuredRetrofitClient {

    const val BASE_URL="http://65.0.55.180/secured/skinmate/v1.0/"

    val retrofitClient: Retrofit.Builder by lazy{

        val levelType: HttpLoggingInterceptor.Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
//            levelType = Level.BODY else levelType = Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl("http://65.0.55.180/secured/skinmate/v1.0/")
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: MyApi by lazy {
        SecuredRetrofitClient.retrofitClient
            .build()
            .create(MyApi::class.java)
    }
}