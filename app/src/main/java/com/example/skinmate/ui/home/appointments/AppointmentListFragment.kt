package com.example.skinmate.ui.home.appointments

import android.R.attr.button
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.data.responses.ResponseInformationXXXXXX
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.checkIn.CheckInActivity
import com.example.skinmate.utils.OnClickInterface
import com.example.skinmate.utils.OnClickInterface_
import com.google.android.material.bottomsheet.BottomSheetDialog


class AppointmentListFragment:BaseFragment(), OnClickInterface,OnClickInterface_ {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.appointment_list, container, false)

        setTitleWithBackButton("My Appointments")
        HomeActivity.bottomNavigationView.visibility = View.VISIBLE
        //HomeActivity.bottomNavigationView.selectedItemId = R.id.navigation_appointment

        val sharedPref: SharedPreferences = requireActivity()!!.getSharedPreferences(
            "SkinMate",
            Context.MODE_PRIVATE
        )
        val custId = sharedPref!!.getString(SignInFragment.CUSTOMER_ID, "none")
        val token = "Bearer " + sharedPref!!.getString(SignInFragment.TOKEN, "none")

        viewModel.getAppointmentList(token, custId!!).observe(requireActivity()) {

            appointmentList=it
            if (it[0].code == 200) {
                Log.v("MAin",it[0].toString())
                if (it[0].responseInformation.isNullOrEmpty()) {
                    replace(R.id.fragment_container, EmptyAppointmentFragment.newInstance(),false)
                }
                else{
                    Log.v("Appim",it[0].responseInformation.toString())
                    val appointmentadapter=AppointmentAdapter(it[0].responseInformation,this,this)
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
        var appointmentList:AppointmentList?=null
        var appointment:ResponseInformationXXXXXX?=null


    }

    override fun getViewPosition(position: Int) {
        appointment = appointmentList!![0].responseInformation[position]
        startActivity(Intent(requireActivity(), CheckInActivity::class.java))

    }

    override fun getViewPosition_(position: Int) {
        appointment= appointmentList!![0].responseInformation[position]
        val BottomDialog= BottomSheetDialog(requireContext())
        BottomDialog.setContentView(R.layout.cancel_reschedule_appointment)
        BottomDialog.show()
        val cancel=BottomDialog.findViewById<TextView>(R.id.Cancel)
        val Reschedule=BottomDialog.findViewById<TextView>(R.id.Reschedule)

        cancel?.setOnClickListener({
            BottomDialog.dismiss()
            replace(R.id.fragment_container,CancelAppointmentFragment.newInstance(),false)
        })
        Reschedule?.setOnClickListener({
            BottomDialog.dismiss()
            replace(R.id.fragment_container,RescheduleAppointmentFragment.newInstance())
        })

    }

}