package com.example.skinmate.ui.home.accountDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.AddInsuranceBinding

class AddInsuranceFragment : BaseFragment() {

    lateinit var addInsuranceBinding: AddInsuranceBinding

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

        return addInsuranceBinding.root
    }
}