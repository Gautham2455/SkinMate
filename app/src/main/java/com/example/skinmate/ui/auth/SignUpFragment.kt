package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SignUpBinding

class SignUpFragment : BaseFragment() {
    private lateinit var signUpBinding: SignUpBinding
    private lateinit var signupViewModel : AuthViewModel
    private lateinit var factory: AuthViewModelFactory

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.sign_up, container, false)
        //signupViewModel=ViewModelProvider(this,factory).get(AuthViewModel::javaClass)

        signUpBinding.signInTv.setOnClickListener {
            add(R.id.fragment_container, SignInFragment.newInstance())
        }

        signUpBinding.proceedBtn.setOnClickListener(){

            if (validateInput()){
                //add opt dialog
            }

        }
        return signUpBinding.root
    }

    private fun validateInput() : Boolean{
        val phone_no=signUpBinding.phoneEt.text.toString()
        val email=signUpBinding.eidEmail.text.toString()
        val password=signUpBinding.setPasswordEt.text.toString()
        val confirm_password=signUpBinding.confirmPasswordEt.toString()
        var flag=true

        val inputValidation = InputValidation()

        if(!inputValidation.isPhoneValid(phone_no)){
            signUpBinding.phoneLayout.setError("Enter valid Phone no")
            flag=false
        }
        if (!inputValidation.isemailValid(email)){
            signUpBinding.eidLayout.setError("Enter valid Email")
            flag=false
        }
        if (inputValidation.passwordValid(password)){
            signUpBinding.setPasswordLayout.setError("Must be more 6 Character")
        }
        if (!inputValidation.isPasswordEqual(password,confirm_password)){
            signUpBinding.confirmPasswordLayout.setError("Passwaord Does Not Match")
        }
        return flag
    }
}