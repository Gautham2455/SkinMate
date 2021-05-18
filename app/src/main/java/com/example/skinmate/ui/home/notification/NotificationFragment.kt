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
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.ui.home.accountDetails.FamilyAdapter

class NotificationFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()
    companion object {
        fun newInstance() =
            NotificationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitle("Notifications")
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")

        viewModel.getNotification("Bearer $token",custId!!).observe(requireActivity()){
            Log.d("notification",it.toString())
//            var notificationList =  arrayListOf<String>(it.get(0).responseInformation.)
            val adapter = notificationAdapter(it.get(0).responseInformation,requireContext())
            val dl = view.findViewById<RecyclerView>(R.id.rv_notification)
            if(it.get(0).responseInformation.size!=0){
                dl.layoutManager = LinearLayoutManager(requireContext())
                dl.setAdapter(adapter)
            }

        }
        return view

    }
}