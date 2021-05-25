package com.example.skinmate.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.appointments.AppointmentListFragment
import com.example.skinmate.ui.home.checkIn.CheckInActivity


class WelcomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding : WelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        setContentView(R.layout.activity_welcome)
        setActionBar(R.id.toolbar_main)
        replace(R.id.fragment_container,AuthenticationFragment.newInstance())




    }

}