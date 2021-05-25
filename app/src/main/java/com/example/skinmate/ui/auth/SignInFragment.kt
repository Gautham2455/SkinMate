package com.example.skinmate.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SigninBinding
import androidx.lifecycle.observe
import com.example.skinmate.ui.home.accountDetails.AccountFragment
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
    val inputValidation = InputValidation()


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

            viewModel.postLoginUser(requestBody).observe(requireActivity()) { loginResponse ->
                Log.v("DEBUG : ", loginResponse.responseInformation)
                val sharedPref:SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPref!!.edit()
                editor.putString(TOKEN,loginResponse.token)
                editor.putString(CUSTOMER_ID,loginResponse.customerId.toString())
                editor.apply()
                editor.commit()
                Log.v("Signin",sharedPref.getString(CUSTOMER_ID," ")!!)
                if (loginResponse.responseMessage) {
                    activity?.finish()
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                }
                else {
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
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
        val touchIdEnabled = sharedPref!!.getString(AccountFragment.TOUCH_ID,"none")
        Log.d("touchidenabled", touchIdEnabled.toString())
        if(touchIdEnabled.toString()=="true") {
            signInBinding.touchId.setImageResource(R.drawable.finger_print)
            signInBinding.touchId.setOnClickListener{
                biometricPrompt.authenticate(promptInfo)
            }
        }

        signInBinding.etPhoneEmail.addTextChangedListener(textWatcher)
        signInBinding.etPassword.addTextChangedListener(textWatcher_pass)
        //signInBinding.btnSignin.addTextChangedListener(textWatcher_btn)

        signInBinding.tvForgotPassword.setOnClickListener {

            add(R.id.fragment_container,ForgotPasswordFragment.newInstance()) }

        return signInBinding.root

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {


        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_mail : String = signInBinding.etPhoneEmail.text.toString()
            val pass: String = signInBinding.etPassword.text.toString()

            signInBinding.textinputPhoneEmail.error = if (inputValidation.isPhoneValid(phone_mail) || inputValidation.isemailValid(phone_mail)) null else "Please enter valid Phone/Email"
            signInBinding.btnSignin.isEnabled = !phone_mail.isEmpty() && !pass.isEmpty()
        }
    }

    private val textWatcher_pass = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {


        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_mail : String = signInBinding.etPhoneEmail.text.toString()
            val pass: String = signInBinding.etPassword.text.toString()

            signInBinding.textinputPassword.error = if (inputValidation.passwordValid(pass)) null else "Must be mininmum 4 Characters"
            signInBinding.btnSignin.isEnabled = !phone_mail.isEmpty() && !pass.isEmpty()
        }
    }
}