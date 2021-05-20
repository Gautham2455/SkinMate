package com.example.skinmate.ui.home.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.checkIn.SymptomCheckFragment

class NotificationDetailsFragment : BaseFragment() {

    companion object{
        fun newInstance() = NotificationDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Notifications")
        val view:View = inflater?.inflate(R.layout.fragment_notification_detial, container, false)
        HomeActivity.bottomNavigationView.visibility = View.GONE

        val message = view.findViewById<TextView>(R.id.tv_message)
        message.setText(NotificationFragment.details!!.Message)

        val date = view.findViewById<TextView>(R.id.tv_appointment_date)
        date.setText(NotificationFragment.details!!.Date)

        val time = view.findViewById<TextView>(R.id.tv_appointment_time)
        time.setText(NotificationFragment.details!!.Time)

        val name = view.findViewById<TextView>(R.id.tv_First_name_)
        name.setText(NotificationFragment.details!!.FirstName)

        val okayBtn =view.findViewById<Button>(R.id.btn_okay)
        okayBtn.setOnClickListener {
            replace(R.id.fragment_container, NotificationFragment.newInstance())
        }

        return view
    }
}