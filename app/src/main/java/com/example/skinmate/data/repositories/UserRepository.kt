package com.example.skinmate.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.skinmate.data.network.RetrofitClient
import com.example.skinmate.data.responses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(application: Application){

    val serviceSetterGetter = MutableLiveData<List<OtpResponse>>()
    val registerEmail = MutableLiveData<List<OtpResponse>>()
    val emailOtp = MutableLiveData<List<OtpResponse>>()
    val checkDplicateUser = MutableLiveData<List<duplicateUserResponse>>()
    val registerUser = MutableLiveData<List<registerUserResponse>>()
    val loginUser = MutableLiveData<List<loginResponse>>()
    val changePassword = MutableLiveData<List<passwordChangeResponse>>()

    fun getServicesApiCall(otp : Int): MutableLiveData<List<OtpResponse>> {

        val call = RetrofitClient.apiInterface.verifyMobleOtp(otp)


        call.enqueue(object: Callback<List<OtpResponse>> {
            override fun onFailure(call: Call<List<OtpResponse>>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<OtpResponse>>,
                response: Response<List<OtpResponse>>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.get(0).responseInformation

                serviceSetterGetter.postValue(response.body())
            }
        })

        return serviceSetterGetter
    }

    fun checkDuplicateUserCall(email : String,phoneNumber : Int) : MutableLiveData<List<duplicateUserResponse>> {

        val call = RetrofitClient.apiInterface.checkDuplicateUser(email,phoneNumber)

        call.enqueue(object : Callback<List<duplicateUserResponse>>{
            override fun onFailure(call: Call<List<duplicateUserResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<duplicateUserResponse>>,
                response: Response<List<duplicateUserResponse>>
            ) {
                TODO("Not yet implemented")

                checkDplicateUser.postValue(response.body() )
            }
        })
        return checkDplicateUser
    }

    fun loginUserCall(id : String,password : String) : MutableLiveData<List<loginResponse>>{
        val call = RetrofitClient.apiInterface.userLogin(id,password)

        call.enqueue(object : Callback<List<loginResponse>>{
            override fun onFailure(call: Call<List<loginResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<loginResponse>>,
                response: Response<List<loginResponse>>
            ) {
                TODO("Not yet implemented")

                loginUser.postValue(response.body())
            }
        })
        return loginUser
    }

    fun changePasswordCall(customerId : Int,oldPassword : String,newPassword :String) : MutableLiveData<List<passwordChangeResponse>>{
        val call= RetrofitClient.apiInterface.changePassword(customerId,oldPassword,newPassword)

        call.enqueue(object : Callback<List<passwordChangeResponse>>{
            override fun onFailure(call: Call<List<passwordChangeResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<passwordChangeResponse>>,
                response: Response<List<passwordChangeResponse>>
            ) {
                    TODO("Not yet implemented")

                    changePassword.postValue(response.body())
            }
        })
        return changePassword
    }

    fun registerUserCall() : MutableLiveData<List<registerUserResponse>>{
        //val call =RetrofitClient.apiInterface.userRegistration()

        return registerUser
    }

    fun registerEmailCall(email : String) :MutableLiveData<List<OtpResponse>>{
        val call = RetrofitClient.apiInterface.registerEmail(email)

        call.enqueue(object : Callback<List<OtpResponse>>{
            override fun onFailure(call: Call<List<OtpResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<OtpResponse>>,
                response: Response<List<OtpResponse>>
            ) {
                TODO("Not yet implemented")

                registerEmail.postValue(response.body())
            }
        })
        return registerEmail
    }

    fun verifyEmailOtpCall(email : String, otp :Int) :MutableLiveData<List<OtpResponse>>{
        val call = RetrofitClient.apiInterface.verifyEmailOtp(email,otp)

        call.enqueue(object : Callback<List<OtpResponse>>{
            override fun onFailure(call: Call<List<OtpResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<List<OtpResponse>>,
                response: Response<List<OtpResponse>>
            ) {
                TODO("Not yet implemented")
                emailOtp.postValue(response.body())
            }
        })
        return emailOtp
    }


    // Singleton Pattern for Repository.
    companion object {
        /**
         *  This is where the EmployeeRepository all callers will receive. Set it to null at first
         *  and make it private so it can't be directly accessed.
         */
        private var INSTANCE: UserRepository? = null

        /**
         * This method checks whether or not INSTANCE is null. If it's not null, it returns the
         * Singleton INSTANCE. If it is null, it creates a new Object, sets INSTANCE equal to that,
         * and returns INSTANCE. From here on out, this method will now return the same INSTANCE,
         * every time.
         */
        fun getInstance(application: Application): UserRepository = INSTANCE ?: kotlin.run {
            INSTANCE = UserRepository(application = application)
            INSTANCE!!
        }
    }

}