package com.example.skinmate.ui.home.notification

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.data.responses.AppointmentList
import com.example.skinmate.data.responses.NotificationDetails
import com.example.skinmate.data.responses.ResponseInformationXXXXXX
import com.example.skinmate.data.responses.notificationResponse
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.accountDetails.FamilyAdapter
import com.example.skinmate.ui.home.appointments.AppointmentListFragment
import com.example.skinmate.utils.OnClickInterface

class NotificationFragment : BaseFragment(), OnClickInterface {

    private val viewModel by viewModels<HomeViewModel>()
    companion object {
        fun newInstance() =
            NotificationFragment()
        var notificationResponse: List<notificationResponse>?=null
        var details: NotificationDetails?=null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        HomeActivity.bottomNavigationView.visibility = View.VISIBLE

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")

        viewModel.getNotification("Bearer $token",custId!!).observe(requireActivity()){
            Log.d("notification",it.toString())
            notificationResponse = it
            val adapter = notificationAdapter(it.get(0).responseInformation,requireContext(),this)
            val dl = view.findViewById<RecyclerView>(R.id.rv_notification)
            if(it.get(0).responseInformation.size!=0){
                dl.layoutManager = LinearLayoutManager(requireContext())
                dl.setAdapter(adapter)
            }

        }
        return view

    }

    override fun getViewPosition(position: Int) {
        details = notificationResponse!!.get(0).responseInformation.get(position)
        replace(R.id.fragment_container,NotificationDetailsFragment.newInstance())
    }
}