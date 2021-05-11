package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignUpFragment
import com.example.skinmate.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.select_doctor.*

class SlectDoctorFragment :BaseFragment(){



    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater?.inflate(R.layout.select_doctor, container, false)

        view.findViewById<Button>(R.id.proceed_btn).setOnClickListener {
            replace(R.id.fragment_container,ScheduleAppointmentFragment.newInstance())
        }

        return view.rootView
    }

    companion object{
        fun newInstance()=SlectDoctorFragment()
    }
}