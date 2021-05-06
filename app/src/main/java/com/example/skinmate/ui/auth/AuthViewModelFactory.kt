package com.example.skinmate.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skinmate.data.repositories.UserRepository

class AuthViewModelFactory (
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel() as T
    }*/
}