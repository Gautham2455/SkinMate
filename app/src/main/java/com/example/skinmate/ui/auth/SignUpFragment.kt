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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
    //private lateinit var signupViewModel : AuthViewModel
    private val viewModel by viewModels<AuthViewModel>()

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

            if (validateInput()){
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
        otpTimer(countTime)
        val resendBtn = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        resendBtn?.setOnClickListener {
            otpTimer(countTime)
        }
        val ConfirmBtn = mobbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            mobbottomSheetDialog.dismiss()
            //var otpnumber : Int = mobbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp).toString().toInt()
            mobOtpVerify(123123)
            }

    }

    private fun otpTimer(countTime: TextView?) {
        val timer = object : CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //display set text trime
                val min  = (millisUntilFinished/60000) % 60
                val sec = (millisUntilFinished/1000) % 60
                countTime?.setText("" + min +"m " + sec + "s").toString()
            }

            override fun onFinish() {
                countTime?.setText("00m 00s")
            }
        }.start()
    }

    private fun mobOtpVerify(otpnumber: Int) {

        //call api to verfify otp sent to mob

        viewModel.getUser(otpnumber).observe(requireActivity()) { otpResponse ->
            Toast.makeText(requireActivity(),otpResponse.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()

        }


        val apiResponse : Boolean = true
        if (apiResponse==true) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_verified)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
            OkayBtn.setOnClickListener {
                dialog.dismiss()
                emailBottomSheetfragment()
            }
            dialog.show()
        }
        else {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_error)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val RetryBtn = dialog.findViewById(R.id.btn_retry) as Button
            RetryBtn.setOnClickListener {
                dialog.dismiss()
                mobOtpBottomSheetfragment()
            }
            dialog.show()
        }
    }

    private fun emailBottomSheetfragment() {

        //call api to send otp to email
        val emailbottomSheetDialog = BottomSheetDialog(requireContext())
        emailbottomSheetDialog.setContentView(R.layout.email_otp)
        emailbottomSheetDialog.show()
        emailbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text =  signUpBinding.eidEmail.text.toString()
        val countTime = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        otpTimer(countTime)
        val resendBtn = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        resendBtn?.setOnClickListener {
            //call api to resend otp
            otpTimer(countTime)
        }
        val ConfirmBtn = emailbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            emailbottomSheetDialog.dismiss()
            val otpemail : Int = emailbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp).toString().toInt()
            emailOtpVerify(1) }

    }

    private fun emailOtpVerify(otpemail: Int) {

        //call api to verify otp sent to email
        var apiResponse : Boolean = true
        if (apiResponse==true) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.emil_otp_verified)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
            OkayBtn.setOnClickListener {
                dialog.dismiss()
                add(R.id.fragment_container,SetupProfileFragment.newInstance())
            }
            dialog.show()
        }
        else {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_error)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val RetryBtn = dialog.findViewById(R.id.btn_retry) as Button
            RetryBtn.setOnClickListener {
                dialog.dismiss()
                emailBottomSheetfragment()
            }
            dialog.show()
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

    }

