package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import androidx.lifecycle.observe
import com.example.skinmate.databinding.SetPasswordBinding

class SetPasswordFragment: BaseFragment() {
    private lateinit var setPasswordBinding: SetPasswordBinding
    private val viewModel by viewModels<AuthViewModel>()
    val ForgotPassword=ForgotPasswordFragment()


    companion object{
        fun newInstance()= SetPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Change Password")
        setPasswordBinding= DataBindingUtil.inflate(inflater, R.layout.set_password,container,false)

        setPasswordBinding.btnSetPwd.setOnClickListener(){

            if(inputValidate()){

                val fpwemail=ForgotPassword.EMAIL.toString()
                val fpwpassword=setPasswordBinding.etConfirmPassword.text.toString()
                viewModel.postUpdatePassword(fpwemail,fpwpassword).observe(requireActivity()){
                    pwdResponse->
                    successfulUpdatePwd(pwdResponse.get(0).responseMessage)
                }
            }
        }

        return setPasswordBinding.root
    }

    private fun successfulUpdatePwd(responseMessage: Boolean?) {
        if(responseMessage==true){
            add(R.id.fragment_container,SuccessMessageFragment.newInstance())
        }

    }

    private fun inputValidate(): Boolean{
        val set_pwd= setPasswordBinding.etNewPassword.text.toString()
        val confirm_pwd= setPasswordBinding.etConfirmPassword.text.toString()
        val inputValidation=InputValidation()

        if (!inputValidation.passwordValid(set_pwd)){
            setPasswordBinding.textinputNewpassword.setError("Must be more 6 Character")
            return false
        }
        else if (!inputValidation.isPasswordEqual(set_pwd,confirm_pwd)){
            setPasswordBinding.textinputConfirmpassword.setError("Password Does Not Match")
            return false
        }
        return true
    }



}