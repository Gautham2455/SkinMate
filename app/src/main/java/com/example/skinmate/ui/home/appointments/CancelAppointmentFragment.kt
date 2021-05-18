package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.data.responses.ResponseInformationXXXXXX
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class CancelAppointmentFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.cancel_appointment,container,false)
        val service=view.findViewById<TextView>(R.id.tv_service)
        val appointment_id=view.findViewById<TextView>(R.id.tv_appointment_id)
        val doctor_info=view.findViewById<TextView>(R.id.doctor_info)
        val date=view.findViewById<TextView>(R.id.date)
        val time=view.findViewById<TextView>(R.id.time)
        val cancel_appointment=view.findViewById<TextView>(R.id.cancel_appointment)

        service.setText(AppointmentListFragment.appointment?.serviceType)
        appointment_id.setText("ID - "+AppointmentListFragment.appointment?.appointmentId.toString())
        doctor_info.setText(AppointmentListFragment.appointment?.firstName+" "+AppointmentListFragment.appointment?.lastName+", "+AppointmentListFragment.appointment?.designation)
        date.setText(AppointmentListFragment.appointment?.dateOfAppointment?.date?.subSequence(0,10))
        time.setText(AppointmentListFragment.appointment?.timeOfAppointment?.time!![0])

        cancel_appointment.setOnClickListener({
            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
                Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
            viewModel.getAppointmentStatus(token,AppointmentListFragment.appointment?.appointmentId.toString(),"Cancled").observe(requireActivity()){
                if(it[0].Code==200)
                    replace(R.id.fragment_container,AppointmentListFragment.newInstance())
                else{

                }
            }
        })



        return view
    }

    companion object{
        fun newInstance()=CancelAppointmentFragment()
    }
}