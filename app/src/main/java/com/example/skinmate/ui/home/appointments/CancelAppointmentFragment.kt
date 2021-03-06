package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.data.responses.ResponseInformationXXXXXX
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CancelAppointmentFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Cancel Appointment")
        val view=inflater?.inflate(R.layout.cancel_appointment,container,false)
        HomeActivity.bottomNavigationView.visibility = View.GONE
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
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")

            val jsonobject= JSONObject()
            jsonobject.put("appointmentId",AppointmentListFragment.appointment?.appointmentId.toString())
            jsonobject.put("status","Cancled")
            val jsonObjectString = jsonobject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.getAppointmentStatus(token,requestBody).observe(this){

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