package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class AppointmentListFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.appointment_list, container, false)
        setTitle("My Appointments")
        val sharedPref: SharedPreferences = requireActivity()!!.getSharedPreferences(
            "SkinMate",
            Context.MODE_PRIVATE
        )
        val custId = sharedPref!!.getString(SignInFragment.CUSTOMER_ID, "none")
        val token = "Bearer " + sharedPref!!.getString(SignInFragment.TOKEN, "none")

        viewModel.getAppointmentList(token, custId!!).observe(requireActivity()) {
            if (it[0].code == 200) {
                if (it[0].responseInformation.isNullOrEmpty()) {
                    replace(R.id.fragment_container, AppointmentFragment.newInstance(),false)

                }
                else{
                    viewModel.getAppointmentList(token,custId!!).observe(requireActivity()){

                        val appointmentadapter=AppointmentAdapter(it[0].responseInformation)
                        val rv_appointment=view.findViewById<RecyclerView>(R.id.rv_appointment_list)
                        rv_appointment.layoutManager= LinearLayoutManager(requireContext())
                        rv_appointment.setAdapter(appointmentadapter)
                        //return view
                    }

                }
            }

        }
        return view






    }
    companion object{
        fun newInstance()=AppointmentListFragment()
    }
}