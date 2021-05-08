package com.example.skinmate.ui.auth

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SigninBinding
import androidx.lifecycle.observe
import com.example.skinmate.ui.home.HomeActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class SignInFragment : BaseFragment() {
    private lateinit var signInBinding: SigninBinding
    private val viewModel by viewModels<AuthViewModel>()


    companion object {
        fun newInstance() = SignInFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Sign In")

        signInBinding = DataBindingUtil.inflate(inflater,R.layout.signin,container,false)
        signInBinding.tvNewUser.setOnClickListener {  add(R.id.fragment_container,SignUpFragment.newInstance()) }


        signInBinding.btnSignin.setOnClickListener {
            val email=signInBinding.etPhoneEmail.text.toString()
            val password=signInBinding.etPassword.text.toString()

            // Create JSON using JSONObject
            val jsonObject = JSONObject()
            jsonObject.put("id", email)
            jsonObject.put("password",password)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            if (validateInput(email,password)){
                viewModel.postLoginUser(requestBody).observe(requireActivity()) { loginResponse ->
                    Log.v("DEBUG : ", loginResponse.responseInformation)
                    if (loginResponse.responseMessage)
                        startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    else
                        signInBinding.textinputPassword.setError("Invalid Phone Number/Password Combination")
                }
            }
        }


        signInBinding.tvForgotPassword.setOnClickListener {

            add(R.id.fragment_container,ForgotPasswordFragment.newInstance()) }

        return signInBinding.root

    }


    private fun validateInput(email : String,password : String) : Boolean{

        var flag=true
        val inputValidation = InputValidation()

        if (!inputValidation.isemailValid(email)){
            signInBinding.textinputPhoneEmail.setError("Please enter a valid Phone Number/Email")
            flag = false
        }
        if (!inputValidation.passwordValid(password)){
            signInBinding.textinputPassword.setError("Must be more 6 Character")
            flag=false
        }

        return flag
    }



}