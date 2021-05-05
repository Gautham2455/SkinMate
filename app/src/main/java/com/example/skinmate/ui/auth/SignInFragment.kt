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

        validate()
        /*signInBinding.btnSignin.setOnClickListener {
            val inputvalidation=InputValidation()
            if (((inputvalidation.isPhoneValid(phno_mail)) || (inputvalidation.isemailValid(phno_mail))))
            {
                signInBinding.etPhoneEmail.setError("Please enter a valid Phone Number/Email")
                Log.e("phno_mail","phno_mail invalid")
                //signInBinding.etPassword.setError("Invalid Phone Number/Password combination")
            }
            if ((inputvalidation.passwordValid(pass))){
                signInBinding.etPassword.setError("Invalid Phone Number/Password combination")
            }
            else
                startActivity(Intent(context,HomeActivity::class.java))
        }*/

        signInBinding.tvForgotPassword.setOnClickListener { add(R.id.fragment_container,ForgotPasswordFragment.newInstance()) }

        return signInBinding.root

    }

    fun validate(){
        signInBinding.btnSignin.setOnClickListener {
            val phno_mail: String=signInBinding.etPhoneEmail.toString()
            val pass: String=signInBinding.etPassword.toString()
            val inputvalidation=InputValidation()
            if (((!inputvalidation.isPhoneValid(phno_mail)) || (!inputvalidation.isemailValid(phno_mail))))
            {
                signInBinding.etPhoneEmail.setError("Please enter a valid Phone Number/Email")
                Log.e("phno_mail","phno_mail invalid")
                //signInBinding.etPassword.setError("Invalid Phone Number/Password combination")
            }
            else if ((!inputvalidation.passwordValid(pass))){
                signInBinding.etPassword.setError("Invalid Phone Number/Password combination")
            }
            else
                startActivity(Intent(context,HomeActivity::class.java))
        }
    }

}