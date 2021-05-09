package com.example.skinmate.ui.auth

import android.content.Context
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
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import androidx.lifecycle.observe
import com.example.skinmate.databinding.SetPasswordBinding
import org.json.JSONObject

class SetPasswordFragment: BaseFragment() {
    private lateinit var setPasswordBinding: SetPasswordBinding
    private val viewModel by viewModels<AuthViewModel>()
    val ForgotPassword=ForgotPasswordFragment()
    val inputValidation=InputValidation()


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
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val fpwemail=sharedPref!!.getString(SignUpFragment.EMAIL_ID," ")
            val fpwpassword=setPasswordBinding.etConfirmPassword.text.toString()
            sharedPref!!.getString(ForgotPasswordFragment.EMAIL!!," ")?.let { it1 ->
                viewModel.postUpdatePassword(fpwemail!!,fpwpassword).observe(requireActivity()){ pwdResponse->
                    successfulUpdatePwd(pwdResponse.firstOrNull()?.Code)
                    if (fpwemail != null) {
                        Log.d("msg",fpwemail)
                    }
                }
            }
        }

        setPasswordBinding.etNewPassword.addTextChangedListener(textWatcher)
        setPasswordBinding.etConfirmPassword.addTextChangedListener(textWatcher)

        return setPasswordBinding.root
    }

    private fun successfulUpdatePwd(responseMessage: Int?) {
        if(responseMessage==200){
            add(R.id.fragment_container,SuccessMessageFragment.newInstance())
        }
        else{
            Toast.makeText(requireContext(),"Unsucessfull",Toast.LENGTH_SHORT).show()
        }

    }

    private val textWatcher= object :TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val new_pwd: String = setPasswordBinding.etNewPassword.text.toString()
            val con_pwd: String = setPasswordBinding.etConfirmPassword.text.toString()

            setPasswordBinding.textinputNewpassword.error= if (inputValidation.passwordValid(new_pwd)) null else "Must be minimum 4 characters"
            setPasswordBinding.textinputConfirmpassword.error= if (inputValidation.isPasswordEqual(new_pwd,con_pwd)) null else "Password does not match"
            setPasswordBinding.btnSetPwd.isEnabled= !new_pwd.isEmpty() && !con_pwd.isEmpty()
        }

    }

}