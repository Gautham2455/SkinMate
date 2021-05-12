package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AppointmentSummary :BaseFragment(){

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.appointment_summary,container,false)


        setTitleWithBackButton("Summary")

        val service=view.findViewById<TextView>(R.id.et_service)
        val time_date=view.findViewById<TextView>(R.id.date_time)
        val insuranceInfo=view.findViewById<EditText>(R.id.et_insurance_information)
        val comments=view.findViewById<EditText>(R.id.et_comments)
        service.setText(HomeFragment.category)
        val payment=view.findViewById<RadioGroup>(R.id.payment)
        time_date.setText(ScheduleAppointmentFragment.appointmentDate)



        val confirmbtn=view.findViewById<Button>(R.id.confirmbtn)

        confirmbtn.setOnClickListener(View.OnClickListener {

            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
                Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
            val payment_type=view.findViewById<RadioButton>(payment.getCheckedRadioButtonId())
            val jsonObject= JSONObject()
            val time=JSONObject()
            val aarray= arrayOf("10:00",":10:20")
            time.put("time",aarray)
            jsonObject.put("customerId",custId!!)
            jsonObject.put("doctorId",SlectDoctorFragment.doctorID)
            jsonObject.put("serviceId",ServicesFragment.subServiceId)
            jsonObject.put("familyProfileId","0")
            jsonObject.put("timeOfAppointment",time)
            jsonObject.put("dateOfAppointment",ScheduleAppointmentFragment.appointmentDate)
            jsonObject.put("paymentType",payment_type.getText())
            jsonObject.put("insuranceInformation",insuranceInfo.getText())
            jsonObject.put("comments",comments.getText())
            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.postAddAppointment(token,requestBody).observe(requireActivity()){
                Log.v("Add",it.get(0).responseMessage.toString())
                replace(R.id.fragment_container,ConfirmationFragment.newInstance())
            }



        })

        return view
    }

    companion object{
        fun newInstance()=AppointmentSummary()
    }
}