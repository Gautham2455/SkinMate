package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SignUpBinding

class SignUpFragment : BaseFragment(){

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val signUpBinding: SignUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.sign_up, container, false)
        signUpBinding.signInTv.setOnClickListener {
            add(
                R.id.fragment_container,
                SignInFragment.newInstance()
            )
        }


        return signUpBinding.root
    }
}