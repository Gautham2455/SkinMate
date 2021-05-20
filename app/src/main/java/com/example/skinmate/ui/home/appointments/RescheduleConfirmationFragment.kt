package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.bookingAppointment.ScheduleAppointmentFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import com.example.skinmate.ui.home.appointments.ConfirmationFragment

class RescheduleConfirmationFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.fragment_confirm_rechedule,container,false)
        val service=view.findViewById<TextView>(R.id.service)
        val existing_date=view.findViewById<TextView>(R.id.existing_date)
        val new_date=view.findViewById<TextView>(R.id.new_date)
        val remaining_time=view.findViewById<TextView>(R.id.remaining_time)
        val rescheduleBtn=view.findViewById<Button>(R.id.rescheduleBtn)

        HomeActivity.bottomNavigationView.visibility = View.GONE
        service.setText(AppointmentListFragment.appointment!!.serviceType!!)
        var app_old_date=AppointmentListFragment.appointment?.dateOfAppointment?.date?.subSequence(0,10).toString()+", "+AppointmentListFragment.appointment?.timeOfAppointment!!.time.firstOrNull()+" EST"
        existing_date.setText(app_old_date)
        var app_new_date=RescheduleAppointmentFragment.appointmentDate+", " +RescheduleAppointmentFragment.appointmentSlots[0]+" EST"
        new_date.setText(app_new_date)

        rescheduleBtn.setOnClickListener({
            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences(
                "SkinMate",
                Context.MODE_PRIVATE
            )
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN, "none")
            val jsonObject= JSONObject()
            val jsonarray= JSONArray(RescheduleAppointmentFragment.appointmentSlots)
            val time=JSONObject()
            time.put("time",jsonarray)
            jsonObject.put("appointmentId",AppointmentListFragment.appointment?.appointmentId.toString())
            jsonObject.put("dateOfAppointment",RescheduleAppointmentFragment.appointmentDate)
            jsonObject.put("timeOfAppointment",time)
            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.putReschedule(token,requestBody).observe(requireActivity()){
                if(it[0].Code==200){
                    replace(R.id.fragment_container,ConfirmationFragment.newInstance())
                }
            }
        })




        return view
    }
    companion object{
        fun newInstance()=RescheduleConfirmationFragment()
    }
}