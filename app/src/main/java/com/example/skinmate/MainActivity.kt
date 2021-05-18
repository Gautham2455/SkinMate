package com.example.skinmate

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skinmate.ui.auth.WelcomeActivity
import com.example.skinmate.utils.RemainingTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val r:RemainingTime=RemainingTime()
        val simpleDateFormat = SimpleDateFormat("yyyy/M/dd hh:mm")
        val date1: Date = simpleDateFormat.parse("2013/10/10 11:30")
        val date2: Date = simpleDateFormat.parse("2013/10/10 20:35")
        val s=r.printDifference(date1, date2)

        Log.v("datee",s)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 3000)
    }
}