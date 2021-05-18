package com.example.skinmate.ui.home.checkIn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.appointments.AppointmentListFragment
import com.example.skinmate.ui.home.bookingAppointment.ScheduleAppointmentFragment

class CheckInFragment : BaseFragment() {

    companion object{
        fun newInstance() = CheckInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Check In")
        val view:View = inflater?.inflate(R.layout.checkin_screen_2, container, false)

        val treatmentTypee = view.findViewById<TextView>(R.id.tv_treatment_type)
        treatmentTypee.setText(AppointmentListFragment.appointment!!.serviceType)

        val id = view.findViewById<TextView>(R.id.tv_ID_value)
        id.setText(AppointmentListFragment.appointment!!.appointmentId.toString())

        val name = view.findViewById<TextView>(R.id.patient_name)
        if(AppointmentListFragment.appointment!!.familyFirstName.isNullOrEmpty())
            name.setText("Self")
        else
            name.setText(AppointmentListFragment.appointment!!.familyFirstName + " " + AppointmentListFragment.appointment!!.familyLastName)

        val doc = view.findViewById<TextView>(R.id.tv_doctor_name)
        doc.setText(AppointmentListFragment.appointment!!.firstName + " " + AppointmentListFragment.appointment!!.lastName + " " + AppointmentListFragment.appointment!!.designation)

        val date = view.findViewById<TextView>(R.id.tv_appointment_date)
        date.setText(AppointmentListFragment.appointment!!.dateOfAppointment.date.subSequence(0,10))
        Log.d("date",AppointmentListFragment.appointment!!.dateOfAppointment.date.subSequence(0,10).toString())

        val time = view.findViewById<TextView>(R.id.tv_appointment_time)
        time.setText(AppointmentListFragment.appointment!!.timeOfAppointment.time.get(0))


        val checkInBtn =view.findViewById<Button>(R.id.proceed_btn)
        checkInBtn.setOnClickListener {
            replace(R.id.fragment_container, SymptomCheckFragment.newInstance())
        }

        return view
    }
}