package com.example.skinmate.ui.home.appointments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.checkIn.CheckInActivity

class EmptyAppointmentFragment : BaseFragment() {
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance() =
            EmptyAppointmentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("My Appointments")


        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        val schedule_appointment=view.findViewById<Button>(R.id.schedule_appointment)
        schedule_appointment.setOnClickListener({
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
        })



        return view

    }

}