package com.example.skinmate.ui.home.checkIn

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SetupProfileFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.appointments.AppointmentListFragment

class CheckDoneFragment : BaseFragment() {

    companion object {
        fun newInstance() = CheckDoneFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Check In")
        val view: View = inflater?.inflate(R.layout.checkin_screen_4, container, false)

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
        date.setText(
            AppointmentListFragment.appointment!!.dateOfAppointment.date.subSequence(
                0,
                10
            )
        )
        Log.d(
            "date",
            AppointmentListFragment.appointment!!.dateOfAppointment.date.subSequence(0, 10)
                .toString()
        )

        val time = view.findViewById<TextView>(R.id.tv_appointment_time)
        time.setText(AppointmentListFragment.appointment!!.timeOfAppointment.time.get(0))

        val checkInBtn = view.findViewById<CardView>(R.id.checkin_complete)
        checkInBtn.setOnClickListener {
            activity?.finish()
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
        }


        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_walk_in)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val OkayBtn = dialog.findViewById(R.id.btn_okay) as Button
        OkayBtn.setOnClickListener {
            dialog.dismiss()
        }


        return view
    }
}