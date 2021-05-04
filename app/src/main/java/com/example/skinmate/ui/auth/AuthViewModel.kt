package com.example.skinmate.ui.auth

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.databinding.EnterDetailsBinding
import com.example.skinmate.ui.home.HomeActivity

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null
    var phonenumber: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var dob: String? = null
    var mailingaddress: String? = null
    var insuranceinfo: String? = null
    var emergencycontactname: String? = null
    var emergencyphonenumber: String? = null


    fun onLoginBtnClick(view : View){

        //Login

    }

    fun  onSignUpBtnClick(view : View){

        //SignUp
    }


}