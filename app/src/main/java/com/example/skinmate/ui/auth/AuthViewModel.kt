package com.example.skinmate.ui.auth


import android.app.Application
import android.appwidget.AppWidgetManager.getInstance
import android.view.View
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skinmate.data.repositories.UserRepository

import com.example.skinmate.data.responses.OtpResponse
import com.example.skinmate.data.responses.*
import kotlinx.coroutines.Dispatchers


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private  val servicesLiveData = UserRepository.getInstance(application)
    var registerEmailDdata : MutableLiveData<OtpResponse>? =null
    var verifyEmaiilOtpData :MutableLiveData<OtpResponse>? =null
    var loginUserData : MutableLiveData<loginResponse>? = null
    var changePasswordData  : MutableLiveData<passwordChangeResponse>? =null
    var checkDplicateUserData : MutableLiveData<duplicateUserResponse>? = null
    var registerUserData : MutableLiveData<registerUserResponse>? = null


    fun getUser(otp: Int) : LiveData<OtpResponse>: LiveData<List<OtpResponse>> = liveData(Dispatchers.IO) {
        emitSource(servicesLiveData.getServicesApiCall(otp))
    }

    /*fun postRegisterEmail(email : String) : LiveData<OtpResponse>? {
        registerEmailDdata = UserRepository.registerEmailCall(email)
        return registerEmailDdata
    }

    fun postLoginUser(id : String,password : String) : LiveData<loginResponse>? {
        loginUserData = UserRepository.loginUserCall(id,password)
        return  loginUserData
    }

    fun postChangePassword(customerId : Int,oldPassword : String,newPassword :String) :LiveData<passwordChangeResponse>?{
        changePasswordData = UserRepository.changePasswordCall(customerId,oldPassword,newPassword)
        return changePasswordData
    }

    fun postCheckDuplicateUser(email : String,phoneNumber : Int) : LiveData<duplicateUserResponse>? {
        checkDplicateUserData = UserRepository.checkDuplicateUserCall(email,phoneNumber)
        return checkDplicateUserData
    }

    fun posttRegisterUser() : LiveData<registerUserResponse>? {
        //
        return registerUserData
    }

    fun postVerifyEmailOtp(email : String, otp :Int) : LiveData<OtpResponse>? {
        verifyEmaiilOtpData = UserRepository.verifyEmailOtpCall(email,otp)
        return verifyEmaiilOtpData

    }*/

}