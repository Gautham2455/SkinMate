package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils.lastIndexOf
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.appointments.AppointmentListFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ConfirmationFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater?.inflate(R.layout.appointment_confirmation,container,false)
        HomeActivity.bottomNavigationView.visibility = View.GONE
        val doneBtn=view.findViewById<Button>(R.id.done_btn)
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")

        val doctor_info=view.findViewById<TextView>(R.id.doctor_info)
        val tv_medical_id=view.findViewById<TextView>(R.id.tv_medical_id)
        val date=view.findViewById<TextView>(R.id.date)
        val time=view.findViewById<TextView>(R.id.time)

        tv_medical_id.setText("ID - "+SlectDoctorFragment.doctorID)
        date.setText(ScheduleAppointmentFragment.appointmentDate?.split(" ")?.getOrNull(0))
        time.setText(ScheduleAppointmentFragment.appointmentSlots.getOrNull(0))


        doneBtn.setOnClickListener(View.OnClickListener {
            replace(R.id.fragment_container,AppointmentListFragment.newInstance())
        })

        return view
    }

    companion object{
        fun newInstance()=ConfirmationFragment()
        var appointments:AppointmentList?=null
        var lastIntex:Int=0
    }
}