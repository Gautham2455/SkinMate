package com.example.skinmate.ui.home.bookingAppointment

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeViewModel

class ScheduleAppointmentFragment :BaseFragment(){

    private val viewModel by viewModels<HomeViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater?.inflate(R.layout.schedule_appointment, container, false)

        val caldendar= view.findViewById<CalendarView>(R.id.date_picker_actions)
        val today = Calendar.getInstance().timeInMillis
        caldendar.setMinDate(today)




        val proocdBtn=view.findViewById<Button>(R.id.proceedBtn)
        proocdBtn.setOnClickListener {

            var day=caldendar.getDate()
            Log.v("DAte",day.toString())
            replace(R.id.fragment_container, AppointmentSummary.newInstance())
        }

        return view
    }

    companion object{
        fun newInstance()=ScheduleAppointmentFragment()
    }
}