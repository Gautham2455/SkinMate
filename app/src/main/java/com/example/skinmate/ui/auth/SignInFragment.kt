package com.example.skinmate.ui.auth

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.util.concurrent.Executor


class SignInFragment : BaseFragment() {
    private lateinit var signInBinding: SigninBinding
    private val viewModel by viewModels<AuthViewModel>()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


    companion object {
        fun newInstance() = SignInFragment()
        const val CUSTOMER_ID:String="customerId"
        const val TOKEN:String="token"
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
        executor = ContextCompat.getMainExecutor(requireContext())



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
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor =  sharedPref!!.edit()
                    editor.putString(TOKEN,loginResponse.token)
                    editor.putString(CUSTOMER_ID,loginResponse.customerId)
                    editor.commit()
                    if (loginResponse.responseMessage)
                        startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    else
                        signInBinding.textinputPassword.setError("Invalid Phone Number/Password Combination")
                }
            }
        }

        biometricPrompt= BiometricPrompt(this@SignInFragment,executor,object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(),"Authentication Error: $errString",Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(),"Authentication Success",Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireActivity(), HomeActivity::class.java))


            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(),"Authentication Failed!!",Toast.LENGTH_SHORT).show()

            }

        })


        promptInfo=BiometricPrompt.PromptInfo.Builder()
            .setTitle("Finger Print Authentication")
            .setSubtitle("Login using fingerprint")
            .setNegativeButtonText("Use App password").build()

        signInBinding.touchId.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)

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