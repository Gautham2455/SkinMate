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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.familyMemberListItem
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.OnClickInterface
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class AppointmentSummary :BaseFragment(),OnClickInterface{

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
        service.setText(HomeFragment.MainService)
        val payment=view.findViewById<RadioGroup>(R.id.payment)
        val appointmentFor=view.findViewById<EditText>(R.id.appoint_for)
        val confirmbtn=view.findViewById<Button>(R.id.confirmbtn)
        val FamilyBottomSheet=BottomSheetDialog(requireContext())

        time_date.setText(ScheduleAppointmentFragment.appointmentDate)


        appointmentFor.setOnClickListener(View.OnClickListener {
            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
                Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")

            FamilyBottomSheet.setContentView(R.layout.bootomsheet_familylist)

            val add_Family=FamilyBottomSheet.findViewById<TextView>(R.id.tv_add_Family)
            add_Family!!.setOnClickListener(View.OnClickListener {
                FamilyBottomSheet.dismiss()

            })


            viewModel.getFamilyMembersList(token,custId!!).observe(requireActivity()){
                familyList=it
                if(!it.get(0).responseInformation.isEmpty()){
                    val rv_names=FamilyBottomSheet.findViewById<RecyclerView>(R.id.rv_familyList)
                    val namesAdapter=FamilyNamesAdapter(requireContext(),it.get(0).responseInformation,this)
                    rv_names?.layoutManager= LinearLayoutManager(requireContext())
                    rv_names?.setAdapter(namesAdapter)
                    rv_names!!.setOnClickListener(View.OnClickListener {
                        FamilyBottomSheet.dismiss()
                    })
                }
                Log.v("family",it.get(0).responseInformation[0].firstName)
                FamilyBottomSheet.show()
            }


        })

        confirmbtn.setOnClickListener(View.OnClickListener {

            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
                Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
            var payment_type:String="Self"

            when(payment.getCheckedRadioButtonId()){
                R.id.self_pay->payment_type="self"
                R.id.pay_insurance->payment_type="insurance"
            }
            val jsonObject= JSONObject()
            val jsonarray=JSONArray(ScheduleAppointmentFragment.appointmentSlots)
            val time=JSONObject()
            time.put("time",jsonarray)
            jsonObject.put("customerId",custId!!)
            jsonObject.put("doctorId",SlectDoctorFragment.doctorID)
            jsonObject.put("serviceId",ServicesFragment.subServiceId)
            jsonObject.put("familyProfileId",familyProfileId)
            jsonObject.put("timeOfAppointment",time)
            jsonObject.put("dateOfAppointment",ScheduleAppointmentFragment.appointmentDate)
            jsonObject.put("paymentType",payment_type)
            jsonObject.put("insuranceInformation",insuranceInfo.getText())
            jsonObject.put("comments",comments.getText())
            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.postAddAppointment(token,requestBody).observe(requireActivity()){
                Log.v("Add",it.get(0).responseMessage.toString())
                if(it.get(0).responseMessage==true){
                    replace(R.id.fragment_container,ConfirmationFragment.newInstance())
                }
                else{
                    Toast.makeText(requireContext(),"Not scheduled",0).show()
                }
            }



        })

        return view
    }

    companion object{
        var familyList:List<familyMemberListItem>?=null
        var familyProfileId:String?=null
        fun newInstance()=AppointmentSummary()
    }

    override fun getViewPosition(position: Int) {
        familyProfileId= familyList!!.get(0).responseInformation[position].familyProfileId.toString()
    }
}