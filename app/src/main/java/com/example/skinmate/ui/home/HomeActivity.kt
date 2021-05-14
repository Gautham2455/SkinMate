package com.example.skinmate.ui.home

import android.os.Bundle
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.ui.home.accountDetails.AccountFragment
import com.example.skinmate.ui.home.appointments.AppointmentListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : BaseActivity() {

    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setActionBar(R.id.toolbar_main)
        replace(R.id.fragment_container, HomeFragment.newInstance())
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> replace(R.id.fragment_container,HomeFragment.newInstance())
                R.id.navigation_appointment -> replace(R.id.fragment_container,
                    AppointmentListFragment.newInstance())
                R.id.navigation_notification -> replace(R.id.fragment_container,NotificationFragment.newInstance())
                R.id.navigation_account -> replace(R.id.fragment_container,
                    AccountFragment.newInstance())
            }
            true
        }

    }


}