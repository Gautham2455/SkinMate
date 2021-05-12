package com.example.skinmate.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.LandingScreenBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.bookingAppointment.ServicesFragment
import kotlinx.android.synthetic.main.landing_screen.view.*


class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var ladingScreenBinding:LandingScreenBinding
    private val viewModel by viewModels<HomeViewModel>()

    companion object {
        var category:String?=null
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()

        val view=inflater?.inflate(R.layout.landing_screen,container,false)

        val card1=view.findViewById<TextView>(R.id.tv_telehealth_video)
        val card2=view.findViewById<TextView>(R.id.tv_medical)
        val card3=view.findViewById<TextView>(R.id.tv_cosmetic)
        val card4=view.findViewById<TextView>(R.id.tv_skin_care)
        val card5=view.findViewById<TextView>(R.id.tv_5)
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val token="Bearer "+sharedPref!!.getString(SignInFragment.TOKEN,"none")
        viewModel.getMAinServices(token).observe(requireActivity()){
             card1.setText(it[0].responseInformation[0].serviceType)
            card2.setText(it[0].responseInformation[1].serviceType)
            card3.setText(it[0].responseInformation[2].serviceType)
            card4.setText(it[0].responseInformation[3].serviceType)
            card5.setText(it[0].responseInformation[3].serviceType)

        }

        view.cosmetic_card.setOnClickListener(this)
        view.medical_card.setOnClickListener(this)
        view.skin_care_card.setOnClickListener(this)
        view.telehealth_video_card.setOnClickListener(this)


        return view

    }

    override fun onClick(p0: View?) {
        val id=p0!!.getId()
        when(id){
            R.id.telehealth_video_card -> category="Telehealth Video"
            R.id.medical_card -> category="Medical"
            R.id.cosmetic_card -> category="Cosmetic Appointment"
            R.id.skin_care_card -> category="Superficial Radiation Therapy"
            R.id.card_5 -> category="Skin Care"
        }
        replace(R.id.fragment_container,ServicesFragment.newInstance())

    }

}