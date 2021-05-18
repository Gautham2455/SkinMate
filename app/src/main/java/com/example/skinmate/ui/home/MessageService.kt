package com.example.skinmate.ui.home

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        Log.d("Token", "Refreshed token: $s")
    }

}