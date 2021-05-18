package com.example.skinmate.ui.home.checkIn

import android.os.Bundle
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.ui.auth.AuthenticationFragment

class CheckInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_checkin)
        setActionBar(R.id.toolbar_main)

        replace(R.id.fragment_container, CheckInFragment.newInstance())

    }
}