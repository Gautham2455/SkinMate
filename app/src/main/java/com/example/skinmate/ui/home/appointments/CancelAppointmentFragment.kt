package com.example.skinmate.ui.home.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.data.responses.ResponseInformationXXXXXX

class CancelAppointmentFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.cancel_appointment,container,false)

        return view
    }

    companion object{
        fun newInstance()=CancelAppointmentFragment()
    }
}