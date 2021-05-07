package com.example.skinmate.ui.auth

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.EnterDetailsBinding
import com.example.skinmate.ui.home.HomeActivity
import java.util.*

class SetupProfileFragment : BaseFragment() {

    private val viewModel by viewModels<AuthViewModel>()

    var firstname : String?=null
    var lastname : String?=null
    var dateofbirth : String?=null
    var mailingaddress : String?=null
    var insuranceinfo : String?=null
    var emergencycontactname : String?=null
    var emergencyphonenumber : String?=null
    var bloodgroup_user : String?=null
    var gender :String?= null

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
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        var bloodgroups=resources.getStringArray(R.array.Bloodgroup)
        var arrayAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu,bloodgroups)
        enterDetailsBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)




        enterDetailsBinding.imageViewGenderMale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderMale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=true
                gender = enterDetailsBinding.tvGenderMale.text.toString()
            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
            }

        }

        enterDetailsBinding.imageViewGenderFemale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=true
                gender = enterDetailsBinding.tvGenderFemale.text.toString()
            }
            else {
            enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
            }

        }
        enterDetailsBinding.imageViewGenderOther.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderOther.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=true
                gender = enterDetailsBinding.tvGenderOther.text.toString()
            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
            }

        }
        enterDetailsBinding.etDob.setOnClickListener {
            var dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                var mmMonth = mMonth+1
                dateofbirth = "$mDay/$mmMonth/$mYear"
                enterDetailsBinding.etDob.setText(dateofbirth)
            },year,month,day)
            dpd.show()

        }



        enterDetailsBinding.btnCreateMyAcnt.setOnClickListener {
            firstname=enterDetailsBinding.etFirstName.text.toString()
            lastname=enterDetailsBinding.etLastName.text.toString()
            mailingaddress=enterDetailsBinding.etMailingAddress.text.toString()
            insuranceinfo=enterDetailsBinding.etInsuranceInfo.text.toString()
            emergencycontactname=enterDetailsBinding.etEmergencyContactName.text.toString()
            emergencyphonenumber=enterDetailsBinding.etEmergencyContactNumber.text.toString()
            bloodgroup_user=enterDetailsBinding.autocompleteBloodGrp.text.toString()


            if (!inputval.isPhoneValid(emergencyphonenumber!!)) {
                enterDetailsBinding.EmergencyNumberLayout.setError("Please Enter a valid Phone number")

            }
            else {


                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

                viewModel.postRegisterUser(sharedPref!!.getInt(SignUpFragment.MOB_NO!!,0),
                    sharedPref!!.getString(SignUpFragment.EMAIL_ID!!,"none")!!,firstname!!,lastname!!,
                gender!!,dateofbirth!!,bloodgroup_user!!,"Android",sharedPref!!.getString(SignUpFragment.USER_PASSWORD!!,null)!!,
                mailingaddress!!,emergencyphonenumber!!.toInt(),insuranceinfo!!,emergencyphonenumber!!).observe(requireActivity()){
                    if(it.get(0).responseMessage)
                        startActivity(Intent(context, HomeActivity::class.java))
                }

            }

        }
            return enterDetailsBinding.root





    }
}