package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ChangePasswordBinding
import com.example.skinmate.databinding.SetPasswordBinding

class SetPasswordFragment: BaseFragment() {
    private lateinit var setPasswordBinding: SetPasswordBinding

    companion object{
        fun newInstance()= SetPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setPasswordBinding= DataBindingUtil.inflate(inflater, R.layout.set_password,container,false)

        setPasswordBinding.btnSetPwd.setOnClickListener(){

            if(inputValidate()){
                add(R.id.fragment_container,SuccessMessageFragment.newInstance())
            }
        }

        return setPasswordBinding.root
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