package com.example.skinmate.ui.home.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skinmate.BaseFragment
import com.example.skinmate.R

class AppointmentFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            AppointmentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitle("My Appointments")
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        return view

    }
}