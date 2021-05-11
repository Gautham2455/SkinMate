package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeViewModel

class AppointmentSummary :BaseFragment(){

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.appointment_summary,container,false)


        val confirmbtn=view.findViewById<Button>(R.id.confirmbtn)

        confirmbtn.setOnClickListener(View.OnClickListener {
            replace(R.id.fragment_container,ConfirmationFragment.newInstance())
        })

        return view
    }

    companion object{
        fun newInstance()=AppointmentSummary()
    }
}