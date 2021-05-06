package com.example.skinmate.ui.auth

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ForgotPasswordBinding
import com.example.skinmate.databinding.SigninBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ForgotPasswordFragment : BaseFragment() {
    private lateinit var forgotPasswordBinding: ForgotPasswordBinding
    private lateinit var signinBinding: SigninBinding

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forgotPasswordBinding = DataBindingUtil.inflate(inflater,R.layout.forgot_password,container,false)

        forgotPasswordBinding.btnForgotPw.setOnClickListener(){

            val phone_mail : String = forgotPasswordBinding.etForgotpw.text.toString()
            val signin_phone_mail = signinBinding.etPhoneEmail.text.toString()

            if (phone_mail == signin_phone_mail)
                mobOtpBottomSheetfragment()

        }

        return forgotPasswordBinding.root
    }

    private fun mobOtpBottomSheetfragment() {

        //call api to send otp to mob

        val mobbottomSheetDialog = BottomSheetDialog(requireContext())
        mobbottomSheetDialog.setContentView(R.layout.mobile_otp)
        mobbottomSheetDialog.show()
        mobbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text = forgotPasswordBinding.etForgotpw.text.toString()
        val countTime = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_timer)
        otpTimer(countTime)
        val resendBtn = mobbottomSheetDialog.findViewById<TextView>(R.id.tv_Resend_otp)
        resendBtn?.setOnClickListener {
            //call api to resend otp
            otpTimer(countTime)
        }
        val ConfirmBtn = mobbottomSheetDialog.findViewById<Button>(R.id.btn_confirm)
        ConfirmBtn?.setOnClickListener {
            mobbottomSheetDialog.dismiss()
//            val otpnumber : Int = bottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp).toString().toInt()
            mobOtpVerify(1)
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
        emailbottomSheetDialog.findViewById<TextView>(R.id.tv_mob_number_or_mail_id)?.text =  forgotPasswordBinding.etForgotpw.text.toString()
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
//            val otpemail : Int = bottomSheetDialog.findViewById<EditText>(R.id.et_enter_otp).toString().toInt()
            emailOtpVerify(1) }

    }

    private fun emailOtpVerify(otpemail: Int) {

        //call api to verify otp sent to email
        val apiResponse : Boolean = true
        if (apiResponse==true) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.emil_otp_verified)
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
            OkayBtn.setOnClickListener {
                dialog.dismiss()
                add(R.id.fragment_container,SetPasswordFragment.newInstance())
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

}