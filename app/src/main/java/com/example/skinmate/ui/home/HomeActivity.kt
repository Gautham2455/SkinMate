package com.example.skinmate.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.skinmate.BaseActivity
import com.example.skinmate.R
import com.example.skinmate.ui.home.accountDetails.AccountFragment
import com.example.skinmate.ui.home.appointments.EmptyAppointmentFragment
import com.example.skinmate.ui.home.checkIn.CheckInFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

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
                    EmptyAppointmentFragment.newInstance())
                R.id.navigation_notification -> replace(R.id.fragment_container,NavigationFragment.newInstance())
                R.id.navigation_account -> replace(R.id.fragment_container,
                    AccountFragment.newInstance())
            }
            true
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Notification",
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager =
                getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "Instance",
                        "getInstanceId failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                val token = task.result
                Log.d("Instance", token!!)
            })

    }


}