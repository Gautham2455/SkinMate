package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeFragment

class ConfirmationFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater?.inflate(R.layout.appointment_confirmation,container,false)

        val doneBtn=view.findViewById<Button>(R.id.done_btn)

        doneBtn.setOnClickListener(View.OnClickListener {
            replace(R.id.fragment_container,HomeFragment.newInstance(),false)
        })

        return view
    }

    companion object{
        fun newInstance()=ConfirmationFragment()
    }
}