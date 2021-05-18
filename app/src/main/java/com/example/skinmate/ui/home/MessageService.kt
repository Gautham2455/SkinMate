package com.example.skinmate.ui.home

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.skinmate.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        showNotification(
            remoteMessage.notification!!.title,
            remoteMessage.notification!!.body)
    }

    override fun onNewToken(s: String) {
        Log.d("Token", "Refreshed token: $s")
    }

    fun showNotification(
        title: String?,
        message: String?
    ) {
        val intent = Intent(this, HomeActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder =
            NotificationCompat.Builder(this, "Notification")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentText(message)
                .setContentIntent(pendingIntent)
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(100, builder.build())
    }

}