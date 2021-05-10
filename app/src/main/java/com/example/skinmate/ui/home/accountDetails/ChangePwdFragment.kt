package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ChangePasswordBinding
import com.example.skinmate.ui.auth.AuthViewModel
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.SignUpFragment
import com.example.skinmate.ui.auth.SuccessMessageFragment


class ChangePwdFragment : BaseFragment() {

    private lateinit var changePasswordBinding: ChangePasswordBinding
    private val viewModel by viewModels<AuthViewModel>()

    companion object {
        fun newInstance() = ChangePwdFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Change Password")
        changePasswordBinding = DataBindingUtil.inflate(inflater, R.layout.change_password,container,false)

        changePasswordBinding.btnChangePw.setOnClickListener {

            if(inputValidate()){
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID," ").toString()
                val old_pwd = changePasswordBinding.etCurrentPassword.text.toString()
                val new_pwd = changePasswordBinding.etNewPassword.text.toString()

                custId.toString().toIntOrNull()?.let { it1 ->
                    viewModel.postChangePassword(it1,old_pwd,new_pwd).observe(requireActivity()){ pwdResponse -> onSuccesfullChangePwd(pwdResponse.get(0).responseMessage)
                    }
                }

            }
        }

       return changePasswordBinding.root
    }

    private fun onSuccesfullChangePwd(responseMessage: Boolean) {

        if(responseMessage){
            add(R.id.fragment_container, SuccessMessageFragment.newInstance())
        }
        else{
            Toast.makeText(requireContext(),"Incorrect Old Password", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputValidate(): Boolean{
        val new_pwd= changePasswordBinding.etNewPassword.text.toString()
        val confirm_new_pwd= changePasswordBinding.etConfirmPassword.text.toString()
        val inputValidation= InputValidation()

        if (!inputValidation.passwordValid(new_pwd)){
            changePasswordBinding.textinputNewpassword.setError("Must be more than 6 Character")
            return false
        }
        else if (!inputValidation.isPasswordEqual(new_pwd,confirm_new_pwd)){
            changePasswordBinding.textinputConfirmpassword.setError("Password doesn't match")
            return false
        }
        return true
    }
}