package com.example.skinmate.ui.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseActivity
import com.example.skinmate.R


class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding : WelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        setContentView(R.layout.activity_welcome)
        setActionBar(R.id.toolbar_main)

        replace(R.id.fragment_container,AuthenticationFragment.newInstance())

    }
}