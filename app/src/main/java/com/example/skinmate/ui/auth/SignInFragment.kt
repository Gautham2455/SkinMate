package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SigninBinding

class SignInFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val signInBinding : SigninBinding = DataBindingUtil.inflate(inflater,R.layout.signin,container,false)
        signInBinding.tvNewUser.setOnClickListener { add(R.id.fragment_container,SignUpFragment.newInstance()) }
        return signInBinding.root

    }
}