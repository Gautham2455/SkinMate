package com.example.skinmate.ui.home.bookingAppointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SubServiceBinding
import com.example.skinmate.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class ServicesFragment : BaseFragment(),View.OnClickListener{

    private lateinit var subServiceBinding:SubServiceBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        subServiceBinding=DataBindingUtil.inflate(inflater,R.layout.sub_service,container,false)


        subServiceBinding.medicalCard.setOnClickListener(this)
        subServiceBinding.superficialCard.setOnClickListener(this)
        return subServiceBinding.root
    }

    companion object{
        fun newInstance()=ServicesFragment()
    }

    override fun onClick(p0: View?) {
        replace(R.id.fragment_container,SlectDoctorFragment.newInstance())
    }

}