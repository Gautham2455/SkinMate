package com.example.skinmate.ui.home.accountDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ChangePasswordBinding


class ChangePwdFragment : BaseFragment() {

    private lateinit var changePasswordBinding: ChangePasswordBinding

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

       return changePasswordBinding.root
    }
}