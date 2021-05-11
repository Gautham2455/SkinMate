package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.AddInsuranceBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class AddInsuranceFragment : BaseFragment() {

    lateinit var addInsuranceBinding: AddInsuranceBinding
    private val viewModel by viewModels<HomeViewModel>()
    var insuranceInfo : String?=null

    companion object {
        fun newInstance() = AddInsuranceFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Add Insurance")
        addInsuranceBinding = DataBindingUtil.inflate(inflater, R.layout.add_insurance,container,false)

        addInsuranceBinding.btnAddInsurance.setOnClickListener {

            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
                Context.MODE_PRIVATE)
            val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
            val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
            insuranceInfo = addInsuranceBinding.insuranceInformation.text.toString()

            viewModel.postAddInsurance("Bearer $token",custId!!,insuranceInfo!!).observe(requireActivity()){
                if(it.get(0).Code == 200)
                    Toast.makeText(requireActivity(),"Insurance Added Successfully",Toast.LENGTH_LONG).show()
            }

        }



        return addInsuranceBinding.root
    }
}