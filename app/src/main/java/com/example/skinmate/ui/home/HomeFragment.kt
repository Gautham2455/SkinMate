package com.example.skinmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.LadingScreenBinding
import com.example.skinmate.ui.home.bookingAppointment.ServicesFragment
import kotlinx.android.synthetic.main.lading_screen.view.*


class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var ladingScreenBinding: LadingScreenBinding


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

        val view=inflater?.inflate(R.layout.lading_screen,container,false)

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
            R.id.skin_care_card -> category="Skin Care"
        }
        replace(R.id.fragment_container,ServicesFragment.newInstance())

    }

}