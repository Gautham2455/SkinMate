package com.example.skinmate.ui.auth


import android.app.Application
import androidx.lifecycle.*

import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.generalResponse
import com.example.skinmate.data.responses.*
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private  val servicesLiveData = UserRepository.getInstance(application)
    private  val registerEmailDdata = UserRepository.getInstance(application)
    private  val verifyEmaiilOtpData = UserRepository.getInstance(application)
    private  val loginUserData = UserRepository.getInstance(application)
    private  val changePasswordData  = UserRepository.getInstance(application)
    private  val checkDplicateUserData = UserRepository.getInstance(application)
    private  val registerUserData = UserRepository.getInstance(application)
    private val updatepasswordData=UserRepository.getInstance(application)

    fun getUser(otp: Int) : LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(servicesLiveData.getServicesApiCall(otp))
    }

    fun postRegisterEmail(email : String) : LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(registerEmailDdata.registerEmailCall(email))
    }

    fun postLoginUser(requesrBody: RequestBody) : LiveData<loginResponse> = liveData(Dispatchers.IO) {
        emitSource(loginUserData.loginUserCall(requesrBody))
    }

    fun postChangePassword(token:String,customerId : String,oldPassword : String,newPassword :String)
    : LiveData<List<passwordChangeResponse>> = liveData(Dispatchers.IO){
        emitSource(changePasswordData.changePasswordCall(token,customerId,oldPassword ,newPassword))
    }

    fun postCheckDuplicateUser(id : String,phoneNumber : String) : LiveData<List<duplicateUserResponse>> =
        liveData(Dispatchers.IO){
        emitSource(checkDplicateUserData.checkDuplicateUserCall(id,phoneNumber))
    }

    fun postRegisterUser(requesrBody: RequestBody) : LiveData<List<registerUserResponse>> = liveData(Dispatchers.IO) {

        emitSource(registerUserData.registerUserCall(requesrBody))
    }

    fun postVerifyEmailOtp(email : String, otp :Int) : LiveData<List<generalResponse>> = liveData(Dispatchers.IO) {
        emitSource(verifyEmaiilOtpData.verifyEmailOtpCall(  email,otp))
    }

    fun postUpdatePassword(email: String,password: String) : LiveData<List<updatePasswordResponse>> = liveData(Dispatchers.IO) {
        emitSource(updatepasswordData.updatePasswordCall(email, password))
    }

}