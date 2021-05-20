package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.skinmate.ui.home.HomeActivity


class ChangePwdFragment : BaseFragment() {

    private lateinit var changePasswordBinding: ChangePasswordBinding
    private val viewModel by viewModels<AuthViewModel>()
    val inputValidation= InputValidation()

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
        HomeActivity.bottomNavigationView.visibility = View.GONE

        changePasswordBinding.btnChangePw.setOnClickListener {

            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
            val old_pwd = changePasswordBinding.etCurrentPassword.text.toString()
            val new_pwd = changePasswordBinding.etNewPassword.text.toString()
            Log.v("CAhange Password",custId.toString())
            viewModel.postChangePassword("Bearer $token",custId!!,old_pwd,new_pwd).observe(requireActivity()){ pwdResponse ->
                onSuccesfullChangePwd(pwdResponse.get(0).responseMessage)
            }
        }

        changePasswordBinding.etNewPassword.addTextChangedListener(textWatcher)
        changePasswordBinding.etConfirmPassword.addTextChangedListener(textWatcher_conpwd)

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

    private val textWatcher= object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val new_pwd: String = changePasswordBinding.etNewPassword.text.toString()
            val con_pwd: String = changePasswordBinding.etConfirmPassword.text.toString()

            changePasswordBinding.textinputNewpassword.error= if (inputValidation.passwordValid(new_pwd)) null else "Must be minimum 4 characters"
            changePasswordBinding.btnChangePw.isEnabled= !new_pwd.isEmpty() && !con_pwd.isEmpty()
        }

    }

    private val textWatcher_conpwd= object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val new_pwd: String = changePasswordBinding.etNewPassword.text.toString()
            val con_pwd: String = changePasswordBinding.etConfirmPassword.text.toString()

            changePasswordBinding.textinputConfirmpassword.error= if (inputValidation.isPasswordEqual(new_pwd,con_pwd)) null else "Password does not match"
            changePasswordBinding.btnChangePw.isEnabled= !new_pwd.isEmpty() && !con_pwd.isEmpty()
        }

    }


    /* private fun inputValidate(): Boolean{
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
     }*/
}