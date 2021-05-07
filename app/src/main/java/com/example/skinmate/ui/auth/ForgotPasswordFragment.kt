package com.example.skinmate.ui.auth

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
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
import androidx.lifecycle.observe
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.OtpResponse
import com.example.skinmate.databinding.ForgotPasswordBinding
import com.example.skinmate.databinding.SigninBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ForgotPasswordFragment : BaseFragment() {
    private lateinit var forgotPasswordBinding: ForgotPasswordBinding

    private lateinit var signinBinding: SigninBinding
    var EMAIL :String?=null
    private val viewModel by viewModels<AuthViewModel>()


    companion object {
        fun newInstance() = ForgotPasswordFragment()
        const val EMAIL : String = "email"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Forgot Password")
        forgotPasswordBinding = DataBindingUtil.inflate(inflater,R.layout.forgot_password,container,false)

        forgotPasswordBinding.btnForgotPw.setOnClickListener(){
            val inputValidation=InputValidation()

            val phone_mail : String = forgotPasswordBinding.etForgotpw.text.toString()
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor=sharedPref!!.edit()
            editor.putString(EMAIL,phone_mail!!)
            editor.commit()


            if (inputValidation.isPhoneValid(phone_mail)){
                mobOtpBottomSheetfragment()
            }
            else if (inputValidation.isemailValid(phone_mail)){
                viewModel.postRegisterEmail(forgotPasswordBinding.etForgotpw.text.toString()).observe(requireActivity()){
                        otpResponse -> emailRegister(otpResponse.get(0).responseMessage)
                    Toast.makeText(requireActivity(),otpResponse.get(0).responseInformation.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
        return forgotPasswordBinding.root
    }

    private fun mobOtpBottomSheetfragment() {
        val mobbottomSheetDialog = BottomSheetDialog(requireContext())
        mobbottomSheetDialog.setContentView(R.layout.mobile_otp)
        mobbottomSheetDialog.show()
        mobbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text = forgotPasswordBinding.etForgotpw.text.toString()
        val countTime = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        val resendBtn = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        otpTimer(countTime,resendBtn)
        val ConfirmBtn = mobbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            mobbottomSheetDialog.dismiss()
            val mobotp=mobbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp)!!.text.toString().toInt()
            mobOtpVerify(mobotp)
        }

    }

    private fun otpTimer(countTime: TextView?,resendBtn:TextView?) {
        val timer = object : CountDownTimer(90000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val min  = (millisUntilFinished/60000) % 60
                val sec = (millisUntilFinished/1000) % 60
                countTime?.setText("" + min +"m " + sec + "s").toString()
            }

            override fun onFinish() {
                countTime?.setText("00m 00s")
                resendBtn?.isClickable=true
                resendBtn?.setOnClickListener {
                    otpTimer(countTime, resendBtn)
                }
            }
        }.start()
    }

    private fun mobOtpVerify(otpnumber: Int) {

        //call api to verfify otp sent to mob
        viewModel.getUser(otpnumber).observe(requireActivity()) { otpResponse ->
            Toast.makeText(
                requireActivity(),
                otpResponse.get(0).responseInformation.toString(),
                Toast.LENGTH_LONG
            ).show()
            successfulMobOtp(otpResponse.get(0).responseMessage)
        }
    }

    private fun successfulMobOtp(responseMessage:Boolean?)
    {
        if(responseMessage==true){
            add(R.id.fragment_container,SetPasswordFragment.newInstance())
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
        emailbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text =  forgotPasswordBinding.etForgotpw.text.toString()
        val countTime = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        val resendBtn = emailbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        otpTimer(countTime,resendBtn)
        val ConfirmBtn = emailbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            emailbottomSheetDialog.dismiss()
            val otpemail = emailbottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp)!!.text.toString().toInt()
            emailOtpVerify(forgotPasswordBinding.etForgotpw.text.toString(),otpemail) }

    }

    private fun emailOtpVerify(email:String,otpemail:Int) {

        //call api to verify otp sent to email
        viewModel.postVerifyEmailOtp(email, otpemail).observe(requireActivity()) { otpResponse ->
            successfulEmailOtp(otpResponse.get(0).responseMessage)
        }
    }

    private fun successfulEmailOtp(responseMessageInformation:Boolean?){
        if (responseMessageInformation == true){
            add(R.id.fragment_container,SetPasswordFragment.newInstance())
        }
        else  {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.otp_error)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val RetryBtn = dialog.findViewById(R.id.btn_retry) as Button
            RetryBtn.setOnClickListener {
                dialog.dismiss()
                viewModel.postRegisterEmail(forgotPasswordBinding.etForgotpw.text.toString())
                    .observe(requireActivity()) { otpResponse ->
                        emailRegister(otpResponse.get(0).responseMessage)
                        Toast.makeText(
                            requireActivity(),
                            otpResponse.get(0).responseInformation.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    private fun emailRegister(responseMessage: Boolean?){
        if(responseMessage==true){
            emailBottomSheetfragment()
        }
    }
}