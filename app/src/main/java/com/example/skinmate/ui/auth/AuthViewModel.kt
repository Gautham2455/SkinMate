package com.example.skinmate.ui.auth


import android.app.Application
import android.appwidget.AppWidgetManager.getInstance
import android.view.View
import androidx.lifecycle.*

import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.OtpResponse
import com.example.skinmate.data.responses.*
import kotlinx.coroutines.Dispatchers


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private  val servicesLiveData = UserRepository.getInstance(application)
    private  val registerEmailDdata = UserRepository.getInstance(application)
    private  val verifyEmaiilOtpData = UserRepository.getInstance(application)
    private  val loginUserData = UserRepository.getInstance(application)
    private  val changePasswordData  = UserRepository.getInstance(application)
    private  val checkDplicateUserData = UserRepository.getInstance(application)
    private  val registerUserData = UserRepository.getInstance(application)


    fun getUser(otp: Int) : LiveData<List<OtpResponse>> = liveData(Dispatchers.IO) {
        emitSource(servicesLiveData.getServicesApiCall(otp))
    }

    fun postRegisterEmail(email : String) : LiveData<List<OtpResponse>> = liveData(Dispatchers.IO) {
        emitSource(registerEmailDdata.registerEmailCall(email))
    }

    fun postLoginUser(id : String,password : String) : LiveData<List<loginResponse>> = liveData(Dispatchers.IO) {
        emitSource(loginUserData.loginUserCall(id,password))
    }

    fun postChangePassword(customerId : Int,oldPassword : String,newPassword :String)
    : LiveData<List<passwordChangeResponse>> = liveData(Dispatchers.IO){
        emitSource(changePasswordData.changePasswordCall(customerId,oldPassword ,newPassword))
    }

    fun postCheckDuplicateUser(email : String,phoneNumber : Int) : LiveData<List<duplicateUserResponse>> =
        liveData(Dispatchers.IO){
        emitSource(checkDplicateUserData.checkDuplicateUserCall(email,phoneNumber))
    }

    fun posttRegisterUser() : LiveData<List<registerUserResponse>> = liveData(Dispatchers.IO) {
        //
        //emitSource(registerUserData.registerEmail())
    }

    fun postVerifyEmailOtp(email : String, otp :Int) : LiveData<List<OtpResponse>> = liveData(Dispatchers.IO) {
        emitSource(verifyEmaiilOtpData.verifyEmailOtpCall(  email,otp))
    }


}