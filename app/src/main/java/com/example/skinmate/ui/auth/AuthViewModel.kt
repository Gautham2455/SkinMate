package com.example.skinmate.ui.auth


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinmate.data.repositories.UserRepository
import com.example.skinmate.data.responses.OtpResponse

class AuthViewModel (
    private val repository: UserRepository
) : ViewModel() {

    var servicesLiveData: MutableLiveData<OtpResponse>? = null

    fun getUser(otp: Int) : LiveData<OtpResponse>? {
        servicesLiveData = UserRepository.getServicesApiCall(otp)
        return servicesLiveData
    }

}