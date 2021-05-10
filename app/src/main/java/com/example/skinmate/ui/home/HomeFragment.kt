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


class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var ladingScreenBinding: LadingScreenBinding


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()

        ladingScreenBinding=DataBindingUtil.inflate(inflater,R.layout.lading_screen,container,false)

        ladingScreenBinding.cosmeticCard.setOnClickListener(this)
        ladingScreenBinding.medicalCard.setOnClickListener(this)
        ladingScreenBinding.skinCareCard.setOnClickListener(this)
        ladingScreenBinding.telehealthVideoCard.setOnClickListener(this)


        return ladingScreenBinding.root

    }

    override fun onClick(p0: View?) {
        replace(R.id.fragment_container,ServicesFragment.newInstance())

    }

}