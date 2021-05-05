package com.example.skinmate.ui.auth

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.skinmate.ui.home.HomeActivity

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var phonenumber: String? = null

    fun onLoginBtnClick(view : View){

        //Login
        //startActivity(Intent(SignInFragment::getContext,HomeActivity::class.java))

    }

    fun  onSignUpBtnClick(view : View){

        //SignUp
    }


}