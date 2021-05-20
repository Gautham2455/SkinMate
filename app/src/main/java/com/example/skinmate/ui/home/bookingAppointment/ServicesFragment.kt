package com.example.skinmate.ui.home.bookingAppointment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SubServiceBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeFragment
import com.example.skinmate.ui.home.HomeViewModel

class ServicesFragment : BaseFragment(),View.OnClickListener{

    private lateinit var subServiceBinding:SubServiceBinding
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        subServiceBinding=DataBindingUtil.inflate(inflater,R.layout.sub_service,container,false)
        HomeActivity.bottomNavigationView.visibility = View.GONE
        setTitleWithBackButton(HomeFragment.MainService)
        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate", Context.MODE_PRIVATE)
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
        viewModel.getSubService("Bearer $token",HomeFragment.MainServiceId!!).observe(requireActivity()){
            Log.v("Serice",it[0].responseInformation.x1)
            if(it[0].responseInformation==null){

            }
            else {
                if (it[0].responseInformation.x3.isNullOrEmpty()) {
                    subServiceBinding.card3.visibility = View.GONE
                    subServiceBinding.tvMedical.setText(it[0].responseInformation.x1.toString()!!)
                    subServiceBinding.tvSuperficialCard.setText(it[0].responseInformation.x2.toString()!!)
                }else {
                    subServiceBinding.card3.visibility = View.VISIBLE
                    subServiceBinding.tvMedical.setText(it[0].responseInformation.x1.toString()!!)
                    subServiceBinding.tvSuperficialCard.setText(it[0].responseInformation.x2.toString()!!)
                    subServiceBinding.tvMedical2.setText(it[0].responseInformation.x3.toString())
                }
            }
        }


        subServiceBinding.medicalCard.setOnClickListener(this)
        subServiceBinding.superficialCard.setOnClickListener(this)
        subServiceBinding.card3.setOnClickListener(this)
        return subServiceBinding.root
    }

    companion object{
        fun newInstance()=ServicesFragment()
        var subServiceId:String?=null
    }

    override fun onClick(p0: View?) {
        val id=p0!!.getId()
        when(id){
            R.id.medical_card -> subServiceId ="1"
            R.id.superficial_card -> subServiceId ="2"
            R.id.card_3 -> subServiceId ="3"

        }
        replace(R.id.fragment_container,SlectDoctorFragment.newInstance())
    }

}