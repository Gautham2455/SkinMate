package com.example.skinmate.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SigninBinding
import com.example.skinmate.ui.home.HomeActivity


class SignInFragment : BaseFragment() {
    private lateinit var signInBinding: SigninBinding

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signInBinding = DataBindingUtil.inflate(inflater,R.layout.signin,container,false)
        signInBinding.tvNewUser.setOnClickListener { add(R.id.fragment_container,SignUpFragment.newInstance()) }

        signInBinding.btnSignin.setOnClickListener {
            if (validateInput()){
                //Call api method
                startActivity(Intent(context,HomeActivity::class.java))
            }
            else
                signInBinding.textinputPassword.setError("Invalid Phone Number/Password Combination")
        }

        signInBinding.tvForgotPassword.setOnClickListener { add(R.id.fragment_container,ForgotPasswordFragment.newInstance()) }



        return signInBinding.root

    }


    private fun validateInput() : Boolean{
        val phone_no=signInBinding.etPhoneEmail.text.toString()
        val email=signInBinding.etPhoneEmail.text.toString()

        val inputValidation = InputValidation()

        if(!inputValidation.isPhoneValid(phone_no)){
            signInBinding.textinputPhoneEmail.setError("Please enter a valid Phone Number/Email")
            return false
        }
        if (!inputValidation.isemailValid(email)){
            signInBinding.textinputPhoneEmail.setError("Please enter a valid Phone Number/Email")
            return false
        }

        return true
    }

}