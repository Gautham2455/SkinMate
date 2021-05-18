package com.example.skinmate.utils

import android.util.Log
import java.util.*

class RemainingTime {


    fun printDifference(startDate: Date, endDate: Date) :String{
        var different = endDate.time - startDate.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli

        if(elapsedDays.toInt()==0){
            return "$elapsedHours Hour $elapsedMinutes min"
        }
        else
            return "$elapsedDays Day $elapsedHours Hour $elapsedMinutes min"
    }
}