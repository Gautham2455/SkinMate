package com.example.skinmate.ui.auth

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SignUpBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialog

class SignUpFragment : BaseFragment() {


    private lateinit var signUpBinding: SignUpBinding
    private val viewModel by viewModels<AuthViewModel>()
    val inputValidation = InputValidation()

    var EMAIL :String?=null
    var PHONE_NO : String?= null
    var PASSWORD : String?=null
    var CONFIRM_PASSWORRRD  : String?=null

    companion object {
        fun newInstance() = SignUpFragment()
        const val EMAIL_ID: String = "emaiil_id"
        const val MOB_NO: String = "phoone_no"
        const val USER_PASSWORD: String = "password"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Sign Up")
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.sign_up, container, false)

        signUpBinding.phoneEt.addTextChangedListener(textWatcher)
        signUpBinding.eidEmail.addTextChangedListener(textWatcher_mail)
        signUpBinding.setPasswordEt.addTextChangedListener(textWatcher_pass)
        signUpBinding.confirmPasswordEt.addTextChangedListener(textWatcher_conpwd)

        signUpBinding.signInTv.setOnClickListener {
            replace(R.id.fragment_container, SignInFragment.newInstance())
        }

        signUpBinding.proceedBtn.setOnClickListener() {
            PHONE_NO = signUpBinding.phoneEt.text.toString()
            EMAIL=signUpBinding.eidEmail.text.toString()
            PASSWORD=signUpBinding.setPasswordEt.text.toString()
            CONFIRM_PASSWORRRD=signUpBinding.confirmPasswordEt.toString()
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPref!!.edit()
            editor.putString(EMAIL_ID,EMAIL!! )
            PHONE_NO?.let { it1 -> editor.putString(MOB_NO, it1) }
            editor.putString(USER_PASSWORD,PASSWORD!!)
            editor.commit()
            viewModel.postCheckDuplicateUser(EMAIL!!,PHONE_NO!!).observe(requireActivity()){
                if(it.get(0).responseMessage)
                    signUpBinding.confirmPasswordLayout.setError("User Email/Phone Number Already Exist")
                else if(it.get(0).responseMessage ==  false && it.get(0).responseInformation == "1")
                    mobOtpBottomSheetfragment()
                else if(it.get(0).responseMessage == false && it.get(0).responseInformation == "2")
                    replace(R.id.fragment_container,SetupProfileFragment.newInstance())
            }

        }

        return signUpBinding.root
    }

    private fun mobOtpBottomSheetfragment() {
        val mobbottomSheetDialog = BottomSheetDialog(requireContext())
        mobbottomSheetDialog.setContentView(R.layout.mobile_otp)
        mobbottomSheetDialog.show()
        mobbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text =
            signUpBinding.phoneEt.text.toString()
        val countTime = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        val resendBtn = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        otpTimer(countTime, resendBtn)
        val ConfirmBtn = mobbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            mobbottomSheetDialog.dismiss()
            var mobotp =
                mobbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp)!!.text.toString()
                    .toInt()
            mobOtpVerify(mobotp)
        }

    }

    private fun mobOtpVerify(otpnumber: Int) {
        //call api to verfify otp sent to mob
        viewModel.getUser(otpnumber,signUpBinding.phoneEt.text.toString()).observe(requireActivity()) { otpResponse ->
            val responseMessageOtpVerification = otpResponse.get(0).responseMessage!!
            if (otpResponse.get(0).responseMessage!!) {
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.otp_verified)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
                OkayBtn.setOnClickListener {
                    dialog.dismiss()
                   replace(R.id.fragment_container,SetupProfileFragment.newInstance())
                }

            } else {
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.otp_error)
                dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                val RetryBtn = dialog.findViewById(R.id.btn_retry) as Button
                RetryBtn.setOnClickListener {
                    dialog.dismiss()
                    mobOtpBottomSheetfragment()
                }
            }
        }

    }


    private fun validateInput(): Boolean {
        var flag = true


        return flag
    }


    private fun otpTimer(countTime: TextView?, resendBtn: TextView?) {
        val timer = object : CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //display set text trime
                val min = (millisUntilFinished / 60000) % 60
                val sec = (millisUntilFinished / 1000) % 60
                countTime?.setText("" + min + "m " + sec + "s").toString()
            }

            override fun onFinish() {
                countTime?.setText("00m 00s")
                resendBtn?.isClickable = true
                resendBtn?.setOnClickListener {
                    otpTimer(countTime, resendBtn)
                }
            }
        }.start()
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String = signUpBinding.phoneEt.text.toString()
            val mail: String = signUpBinding.eidEmail.text.toString()
            val pass: String = signUpBinding.setPasswordEt.text.toString()
            val confirmpwd: String = signUpBinding.confirmPasswordEt.text.toString()

            signUpBinding.phoneLayout.error =
                if (inputValidation.isPhoneValid(phone_no)) null else "Enter Valid Phone Number"
            signUpBinding.proceedBtn.isEnabled =
                !phone_no.isEmpty() && !mail.isEmpty() && !pass.isEmpty() && !confirmpwd.isEmpty()

        }

    }

    val textWatcher_mail = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String = signUpBinding.phoneEt.text.toString()
            val mail: String = signUpBinding.eidEmail.text.toString()
            val pass: String = signUpBinding.setPasswordEt.text.toString()
            val confirmpwd: String = signUpBinding.confirmPasswordEt.text.toString()

            signUpBinding.eidLayout.error =
                if (inputValidation.isemailValid(mail)) null else "Enter Valid Email"
            signUpBinding.proceedBtn.isEnabled =
                !phone_no.isEmpty() && !mail.isEmpty() && !pass.isEmpty() && !confirmpwd.isEmpty()

        }

    }

    val textWatcher_pass = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String = signUpBinding.phoneEt.text.toString()
            val mail: String = signUpBinding.eidEmail.text.toString()
            val pass: String = signUpBinding.setPasswordEt.text.toString()
            val confirmpwd: String = signUpBinding.confirmPasswordEt.text.toString()

            signUpBinding.setPasswordLayout.error =
                if (inputValidation.passwordValid(pass)) null else "Must be minimum 4 Characters"
            signUpBinding.proceedBtn.isEnabled =
                !phone_no.isEmpty() && !mail.isEmpty() && !pass.isEmpty() && !confirmpwd.isEmpty()

        }

    }

    val textWatcher_conpwd = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String = signUpBinding.phoneEt.text.toString()
            val mail: String = signUpBinding.eidEmail.text.toString()
            val pass: String = signUpBinding.setPasswordEt.text.toString()
            val confirmpwd: String = signUpBinding.confirmPasswordEt.text.toString()

            signUpBinding.confirmPasswordLayout.error = if (inputValidation.isPasswordEqual(
                    pass,
                    confirmpwd
                )
            ) null else "Password does not match"

            signUpBinding.proceedBtn.isEnabled =
                !phone_no.isEmpty() && !mail.isEmpty() && !pass.isEmpty() && !confirmpwd.isEmpty()

        }

    }

}

