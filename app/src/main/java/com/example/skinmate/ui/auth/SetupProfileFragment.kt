package com.example.skinmate.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
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
import com.example.skinmate.ui.home.HomeActivity
import java.util.*

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
        val c=Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)


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


        enterDetailsBinding.imageViewGenderMale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderMale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=true
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#B2BFB8"))
                val gender = enterDetailsBinding.tvGenderMale.text.toString()
            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }

        }

        enterDetailsBinding.imageViewGenderFemale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=true
                //enterDetailsBinding.cardFemale.setBackgroundColor(resources.getColor(R.color.theme_background_light))
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#B2BFB8"))
                val gender = enterDetailsBinding.tvGenderFemale.text.toString()
            }
            else {
            enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }

        }
        enterDetailsBinding.imageViewGenderOther.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderOther.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=true
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#B2BFB8"))
                val gender = enterDetailsBinding.tvGenderOther.text.toString()
            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }

        }
        enterDetailsBinding.etDob.setOnClickListener {
            var dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth+1
                val date = "$mDay/$mmMonth/$mYear"
                enterDetailsBinding.etDob.setText(date)
            },year,month,day)
            dpd.show()

        }



        enterDetailsBinding.btnCreateMyAcnt.setOnClickListener {

            if (!inputval.isPhoneValid(emergencyphonenumber)) {
                enterDetailsBinding.EmergencyNumberLayout.setError("Please Enter a valid Phone number")

            }
            else {
                startActivity(Intent(context, HomeActivity::class.java))
            }

        }
            return enterDetailsBinding.root

    }
}