package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.bottomsheet.BottomSheetDialog

class AppointmentListFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.appointment_list, container, false)
        val BottomDialog= BottomSheetDialog(requireContext())
        setTitleWithBackButton("My Appointments")
        val sharedPref: SharedPreferences = requireActivity()!!.getSharedPreferences(
            "SkinMate",
            Context.MODE_PRIVATE
        )
        val custId = sharedPref!!.getString(SignInFragment.CUSTOMER_ID, "none")
        val token = "Bearer " + sharedPref!!.getString(SignInFragment.TOKEN, "none")

        viewModel.getAppointmentList(token, custId!!).observe(requireActivity()) {
            Log.v("MAin",it[0].toString())
            Log.v("MAin","aaaa")
            if (it[0].code == 200) {
                Log.v("MAin",it[0].toString())
                if (it[0].responseInformation.isNullOrEmpty()) {
                    replace(R.id.fragment_container, EmptyAppointmentFragment.newInstance(),false)
                }
                else{
                    Log.v("Appim",it[0].responseInformation.toString())
                    val appointmentadapter=AppointmentAdapter(it[0].responseInformation)
                    val rv_appointment=view.findViewById<RecyclerView>(R.id.rv_appointment_list)
                    rv_appointment.layoutManager= LinearLayoutManager(requireContext())
                    rv_appointment.setAdapter(appointmentadapter)
                }
            }

        }

        return view

    }
    companion object{
        fun newInstance()=AppointmentListFragment()
    }
}