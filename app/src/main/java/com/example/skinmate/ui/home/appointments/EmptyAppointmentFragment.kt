package com.example.skinmate.ui.home.appointments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class AppointmentFragment : BaseFragment() {
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance() =
            AppointmentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitle("My Appointments")
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")

        val view = inflater.inflate(R.layout.fragment_appointment, container, false)



        return view

    }

}