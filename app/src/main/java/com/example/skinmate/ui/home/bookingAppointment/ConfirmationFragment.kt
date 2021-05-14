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
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel

class ConfirmationFragment:BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater?.inflate(R.layout.appointment_confirmation,container,false)

        val doneBtn=view.findViewById<Button>(R.id.done_btn)
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
        viewModel.getAppointmentList(token,custId!!).observe(requireActivity()){appointmentList->
            val latindex=appointmentList[0].responseInformation.size-1
            Log.v("Con",latindex.toString())
            viewModel.getAppointmentStatus(token,appointmentList[0].responseInformation[latindex].appointmentId.toString(),"Accepted").observe(requireActivity()){

            }
        }

        doneBtn.setOnClickListener(View.OnClickListener {
            replace(R.id.fragment_container,HomeFragment.newInstance(),false)
        })

        return view
    }

    companion object{
        fun newInstance()=ConfirmationFragment()
    }
}