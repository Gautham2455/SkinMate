package com.example.skinmate.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.EnterDetailsBinding
import com.example.skinmate.databinding.SigninBinding
import com.example.skinmate.ui.home.HomeActivity

class SetupProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = SetupProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")
        val enterDetailsBinding : EnterDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.enter_details,container,false)
        val inputval= InputValidation()

        val firstname=enterDetailsBinding.etFirstName.text.toString()
        val lastname=enterDetailsBinding.etLastName.text.toString()
        val dateofbirth=enterDetailsBinding.etDob.text.toString()
        val mailingaddress=enterDetailsBinding.etMailingAddress.text.toString()
        val insuranceinfo=enterDetailsBinding.etInsuranceInfo.text.toString()
        val emergencycontactname=enterDetailsBinding.etEmergencyContactName.text.toString()
        val emergencyphonenumber=enterDetailsBinding.etEmergencyContactNumber.text.toString()

        val bloodgroups=resources.getStringArray(R.array.Bloodgroup)
        val arrayAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu,bloodgroups)
        enterDetailsBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)

        val bloodgroup_user=enterDetailsBinding.autocompleteBloodGrp.text.toString()

        enterDetailsBinding.imageViewGenderFemale.setOnClickListener {
            enterDetailsBinding.ImageViewSelectedGenderMale.setVisibility(View.VISIBLE)
            val gender=enterDetailsBinding.tvGenderMale.text.toString()

        }


        enterDetailsBinding.imageViewGenderFemale.setOnClickListener {
            enterDetailsBinding.ImageViewSelectedGenderFemale.setVisibility(View.VISIBLE)
            val gender=enterDetailsBinding.tvGenderFemale.text.toString()
        }

        enterDetailsBinding.imageViewGenderOther.setOnClickListener {
            enterDetailsBinding.ImageViewSelectedGenderOther.setVisibility(View.VISIBLE)
            val gender=enterDetailsBinding.tvGenderOther.text.toString()
        }


        enterDetailsBinding.btnCreateMyAcnt.setOnClickListener {

            if (!inputval.isPhoneValid(emergencyphonenumber)) {
                enterDetailsBinding.etEmergencyContactNumber.setError("Please Enter a valid Phone number")

            }
            else {
                startActivity(Intent(context, HomeActivity::class.java))
            }

        }
            return enterDetailsBinding.root

    }
}