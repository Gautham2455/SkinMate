package com.example.skinmate.ui.home.checkIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skinmate.BaseFragment
import com.example.skinmate.R

class CheckInFragment : BaseFragment() {

    companion object{
        fun newInstance() = CheckInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater?.inflate(R.layout.checkin_screen_2, container, false)

        return view
    }
}