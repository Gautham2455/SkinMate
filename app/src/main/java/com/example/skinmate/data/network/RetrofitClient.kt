package com.example.skinmate.data.network

import com.example.skinmate.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient  {

    val retrofitClient: Retrofit.Builder by lazy{

        val levelType: HttpLoggingInterceptor.Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
//            levelType = Level.BODY else levelType = Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl("http://65.0.55.180/skinmate/v1.0/")
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiInterface: MyApi by lazy {
        retrofitClient
            .build()
            .create(MyApi::class.java)
    }
}