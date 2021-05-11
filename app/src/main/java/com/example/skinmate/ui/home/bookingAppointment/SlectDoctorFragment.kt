package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.doctorListResponse
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.SignUpFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.OnClickInterface
import kotlinx.android.synthetic.main.select_doctor.view.*

class SlectDoctorFragment :BaseFragment(),OnClickInterface{

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater?.inflate(R.layout.select_doctor, container, false)

        setTitleWithBackButton("Select Doctor")
        var doctorResponse: doctorListResponse?=null

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate", Context.MODE_PRIVATE)
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
        viewModel.getDoctorList(token,ServicesFragment.subServiceId!!).observe(requireActivity()){
            doctorResponse=it

            val doctorAdapter=DoctorAdapter(doctorResponse!![0].responseInformation,requireContext(),this)
            val dl=view.findViewById<RecyclerView>(R.id.doctor_list)
            dl.layoutManager=LinearLayoutManager(requireContext())
            dl.setAdapter(doctorAdapter)
            Log.v("Doctor",doctorResponse!![0].responseInformation[0].firstName.toString())
            Log.v("Doctor",it[0].responseInformation[0].firstName)

        }




        val proocdBtn=view.findViewById<Button>(R.id.proceed_btn)
        proocdBtn.setOnClickListener {
            replace(R.id.fragment_container,ScheduleAppointmentFragment.newInstance())
        }

        return view
    }

    companion object{
        var  doctorID:String?="4"
        fun newInstance()=SlectDoctorFragment()
    }

    override fun getViewPosition(position: Int) {

    }


}