package com.example.skinmate.ui.auth

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SignUpBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class SignUpFragment : BaseFragment() {


    private lateinit var signUpBinding: SignUpBinding
    private val viewModel by viewModels<AuthViewModel>()
    var EMAIL :String?=null
    var PHONE_NO : Int? =null
    var PASSWORD : String?=null
    var CONFIRM_PASSWORRRD  : String?=null

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Sign Up")
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.sign_up, container, false)

        signUpBinding.signInTv.setOnClickListener {
            add(R.id.fragment_container, SignInFragment.newInstance())
        }

        signUpBinding.proceedBtn.setOnClickListener(){

            PHONE_NO=signUpBinding.phoneEt.text.toString().toInt()
            EMAIL=signUpBinding.eidEmail.text.toString()
            PASSWORD=signUpBinding.setPasswordEt.text.toString()
            CONFIRM_PASSWORRRD=signUpBinding.confirmPasswordEt.toString()

            if (validateInput()) {
                //add opt dialog

                mobOtpBottomSheetfragment()
            }

        }

        return signUpBinding.root
    }

    private fun mobOtpBottomSheetfragment() {
        val mobbottomSheetDialog = BottomSheetDialog(requireContext())
        mobbottomSheetDialog.setContentView(R.layout.mobile_otp)
        mobbottomSheetDialog.show()
        mobbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text = signUpBinding.phoneEt.text.toString()
        val countTime = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        val resendBtn = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        otpTimer(countTime,resendBtn)
        val ConfirmBtn = mobbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            mobbottomSheetDialog.dismiss()
            val mobotp = mobbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp)!!.text.toString().toInt()
            mobOtpVerify(mobotp)
            }

    }

    private fun mobOtpVerify(otpnumber: Int) {
        //call api to verfify otp sent to mob
        viewModel.getUser(otpnumber).observe(requireActivity()) { otpResponse ->
            successfulMobOtp(otpResponse.get(0).responseMessage)
        }
    }

    private fun successfulMobOtp(responseMessage: Boolean?) {
        if (responseMessage == true) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_verified)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
            OkayBtn.setOnClickListener {
                dialog.dismiss()
                viewModel.postRegisterEmail(signUpBinding.eidEmail.text.toString()).observe(requireActivity()){
                        otpResponse -> emailRegister(otpResponse.get(0).responseMessage)
                    Toast.makeText(requireActivity(),otpResponse.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
        else {
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


    private fun emailBottomSheetfragment() {

        val emailbottomSheetDialog = BottomSheetDialog(requireContext())
        emailbottomSheetDialog.setContentView(R.layout.email_otp)
        emailbottomSheetDialog.show()
        emailbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text =  signUpBinding.eidEmail.text.toString()
        val countTime = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        val resendBtn = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        otpTimer(countTime,resendBtn)
        val ConfirmBtn = emailbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            emailbottomSheetDialog.dismiss()
            val otpemail = emailbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp)!!.text.toString().toInt()
            emailOtpVerify(signUpBinding.eidEmail.text.toString(),otpemail)
        }
    }

    private fun emailOtpVerify(email:String ,otpemail: Int) {

        viewModel.postVerifyEmailOtp(email,otpemail).observe(requireActivity()){otpResponse ->
            successfulEmailOtp(otpResponse.get(0).responseMessage)
            Toast.makeText(requireActivity(),otpResponse.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()
        }
    }

    private fun successfulEmailOtp(responseMessageInformation:Boolean?) {
        if (responseMessageInformation == true) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.emil_otp_verified)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
            OkayBtn.setOnClickListener {
                dialog.dismiss()
                add(R.id.fragment_container,SetupProfileFragment.newInstance(),false)
            }
        }
        else {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_error)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val RetryBtn = dialog.findViewById(R.id.btn_retry) as Button
            RetryBtn.setOnClickListener {
                dialog.dismiss()
                viewModel.postRegisterEmail(signUpBinding.eidEmail.text.toString()).observe(requireActivity()){
                        otpResponse -> emailRegister(otpResponse.get(0).responseMessage)
                    Toast.makeText(requireActivity(),otpResponse.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun validateInput() : Boolean{
        val phone_no=signUpBinding.phoneEt.text.toString()
        val email=signUpBinding.eidEmail.text.toString()
        val password=signUpBinding.setPasswordEt.text.toString()
        val confirm_password=signUpBinding.confirmPasswordEt.toString()
        var flag=true

        val inputValidation = InputValidation()

        if(!inputValidation.isPhoneValid(phone_no)){
            signUpBinding.phoneLayout.setError("Enter valid Phone no")
            flag=false
        }
        if (!inputValidation.isemailValid(email)){
            signUpBinding.eidLayout.setError("Enter valid Email")
            flag=false
        }
        if (!inputValidation.passwordValid(password)){
            signUpBinding.setPasswordLayout.setError("Must be more 6 Character")
        }
        if (!inputValidation.isPasswordEqual(password,confirm_password)){
            signUpBinding.confirmPasswordLayout.setError("Passwaord Does Not Match")
        }
        return flag
    }

    private fun emailRegister(responseMessage: Boolean?) {
        if(responseMessage==true){
            emailBottomSheetfragment()
        }
    }

    private fun otpTimer(countTime: TextView?,resendBtn: TextView?) {
        val timer = object : CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //display set text trime
                val min  = (millisUntilFinished/60000) % 60
                val sec = (millisUntilFinished/1000) % 60
                countTime?.setText("" + min +"m " + sec + "s").toString()
            }

            override fun onFinish() {
                countTime?.setText("00m 00s")
                resendBtn?.isClickable = true
                resendBtn?.setOnClickListener {
                    otpTimer(countTime,resendBtn)
                }
            }
        }.start()
    }
}

