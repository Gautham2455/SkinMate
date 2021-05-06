package com.example.skinmate.ui.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.databinding.WelcomeBinding

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : WelcomeBinding = DataBindingUtil.setContentView(this, R.layout.welcome)
        setActionBar(R.id.toolbar_main)
        setTitle("")

        binding.btnSignup.setOnClickListener {
            add(R.id.fragment_container,SignUpFragment.newInstance())
        }

        binding.btnSignin.setOnClickListener {
            add(R.id.fragment_container,SignInFragment.newInstance())
        }
    }
}