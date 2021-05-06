package com.example.skinmate.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.skinmate.data.network.RetrofitClient
import com.example.skinmate.data.responses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {

    val serviceSetterGetter = MutableLiveData<OtpResponse>()
    val registerEmail = MutableLiveData<OtpResponse>()
    val emailOtp = MutableLiveData<OtpResponse>()
    val checkDplicateUser = MutableLiveData<duplicateUserResponse>()
    val registerUser = MutableLiveData<registerUserResponse>()
    val loginUser = MutableLiveData<loginResponse>()
    val changePassword = MutableLiveData<passwordChangeResponse>()

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

    fun checkDuplicateUserCall(email : String,phoneNumber : Int) : MutableLiveData<duplicateUserResponse> {

        val call = RetrofitClient.apiInterface.checkDuplicateUser(email,phoneNumber)

        call.enqueue(object : Callback<duplicateUserResponse>{
            override fun onFailure(call: Call<duplicateUserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<duplicateUserResponse>,
                response: Response<duplicateUserResponse>
            ) {
                TODO("Not yet implemented")

                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.responseMessage
            }
        })
        return checkDplicateUser
    }

    fun loginUserCall(id : String,password : String) : MutableLiveData<loginResponse>{
        val call = RetrofitClient.apiInterface.userLogin(id,password)

        call.enqueue(object : Callback<loginResponse>{
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {
                TODO("Not yet implemented")
            }
        })
        return loginUser
    }

    fun changePasswordCall(customerId : Int,oldPassword : String,newPassword :String) : MutableLiveData<passwordChangeResponse>{
        val call= RetrofitClient.apiInterface.changePassword(customerId,oldPassword,newPassword)

        call.enqueue(object : Callback<passwordChangeResponse>{
            override fun onFailure(call: Call<passwordChangeResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<passwordChangeResponse>,
                response: Response<passwordChangeResponse>
            ) {
                TODO("Not yet implemented")
            }
        })
        return changePassword
    }

    fun registerUserCall() : MutableLiveData<registerUserResponse>{
        //val call =RetrofitClient.apiInterface.userRegistration()

        return registerUser
    }

    fun registerEmailCall(email : String) :MutableLiveData<OtpResponse>{
        val call = RetrofitClient.apiInterface.registerEmail(email)

        call.enqueue(object : Callback<OtpResponse>{
            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                TODO("Not yet implemented")
            }
        })
        return registerEmail
    }

    fun verifyEmailOtpCall(email : String, otp :Int) :MutableLiveData<OtpResponse>{
        val call = RetrofitClient.apiInterface.verifyEmailOtp(email,otp)

        call.enqueue(object : Callback<OtpResponse>{
            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                TODO("Not yet implemented")
            }
        })
        return emailOtp
    }

}