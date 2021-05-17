package com.example.skinmate.ui.home

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MessageService : FirebaseMessagingService() {


    override fun onNewToken(s: String) {
        Log.d("Token", "Refreshed token: $s")
    }

}