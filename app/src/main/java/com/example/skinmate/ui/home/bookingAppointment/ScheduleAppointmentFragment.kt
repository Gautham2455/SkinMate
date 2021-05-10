package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.skinmate.BaseFragment
import com.example.skinmate.R

class ScheduleAppointmentFragment :BaseFragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater?.inflate(R.layout.schedule_appointment,container,false)

        val proocdBtn=view.findViewById<Button>(R.id.proceedBtn)
        proocdBtn.setOnClickListener {
            replace(R.id.fragment_container,AppointmentSummary.newInstance())
        }

        return view
    }

    companion object{
        fun newInstance()=ScheduleAppointmentFragment()
    }
}