package com.example.skinmate.data.network

import androidx.constraintlayout.solver.state.State
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object RetrofitClient  {
    const val BASE_URL="http://65.0.55.180/skinmate/v1.0/"

    val retrofitClient: Retrofit.Builder by lazy{

        val levelType: HttpLoggingInterceptor.Level
//        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
//            levelType = Level.BODY else levelType = Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }
    /*var client: OkHttpClient = Builder().addInterceptor(object : Interceptor() {
        @Throws(IOException::class)
        fun intercept(chain: State.Chain): Response? {
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
    }).build()*/

    val apiInterface: MyApi by lazy {
        RetrofitClient.retrofitClient
            .build()
            .create(MyApi::class.java)
    }
}