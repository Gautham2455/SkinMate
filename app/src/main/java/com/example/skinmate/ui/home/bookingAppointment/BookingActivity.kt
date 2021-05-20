package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.ui.home.checkIn.CheckInFragment

class BookingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_booking)
        setActionBar(R.id.toolbar_main)

        replace(R.id.fragment_container, ServicesFragment.newInstance())

    }
}