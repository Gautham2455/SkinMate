package com.example.skinmate.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.skinmate.data.network.RetrofitClient
import com.example.skinmate.data.responses.OtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    val serviceSetterGetter = MutableLiveData<OtpResponse>()

    fun getServicesApiCall(otp : Int): MutableLiveData<OtpResponse> {

        val call = RetrofitClient.apiInterface.verifyMobleOtp(otp)


        call.enqueue(object: Callback<OtpResponse> {
            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<OtpResponse>,
                response: Response<OtpResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.message
            }
        })

        return serviceSetterGetter
    }


}