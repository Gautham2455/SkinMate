package com.example.skinmate.utils

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AlertDialogLayout
import com.example.skinmate.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class NetworkConnection() :BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {

        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        no_internetDialog=Dialog(context)
        no_internetDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        no_internetDialog?.setContentView(R.layout.dialog_no_internet)
        no_internetDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (mobile!!.isConnected || wifi!!.isConnected) {
            no_internetDialog!!.dismiss()
        } else {
            no_internetDialog!!.show()
        }

    }
    companion object{
        var no_internetDialog:Dialog?=null
    }

}