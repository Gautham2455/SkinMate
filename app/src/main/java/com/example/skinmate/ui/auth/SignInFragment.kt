package com.example.skinmate.ui.auth

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SigninBinding
import androidx.lifecycle.observe
import com.example.skinmate.ui.home.HomeActivity


class SignInFragment : BaseFragment() {
    private lateinit var signInBinding: SigninBinding
    private val viewModel by viewModels<AuthViewModel>()

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Sign In")

        signInBinding = DataBindingUtil.inflate(inflater,R.layout.signin,container,false)
        signInBinding.tvNewUser.setOnClickListener {  add(R.id.fragment_container,SignUpFragment.newInstance()) }

        //checkBiometricSupport()

        signInBinding.btnSignin.setOnClickListener {
            val email=signInBinding.etPhoneEmail.text.toString()
            val password=signInBinding.etPassword.text.toString()
            if (validateInput(email,password)){
                viewModel.postLoginUser(email,password).observe(requireActivity()) { loginResponse ->
                    if (loginResponse.get(0).responseMessage)
                        startActivity(Intent(context, HomeActivity::class.java))
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        /*val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true*/

        return true
    }
    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}