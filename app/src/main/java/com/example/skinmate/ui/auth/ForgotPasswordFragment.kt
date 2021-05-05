package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ForgotPasswordBinding

class ForgotPasswordFragment : BaseFragment() {

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val forgotPasswordBinding: ForgotPasswordBinding=DataBindingUtil.inflate(inflater,R.layout.forgot_password,container,false)
        return forgotPasswordBinding.root
    }

}