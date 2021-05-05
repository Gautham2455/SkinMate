package com.example.skinmate.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.OtpResponse


class AuthViewModel() : ViewModel() {

    var servicesLiveData: MutableLiveData<OtpResponse>? = null

    fun getUser(otp: Int) : LiveData<OtpResponse>? {
        servicesLiveData = UserRepository.getServicesApiCall(otp)
        return servicesLiveData
    }

}