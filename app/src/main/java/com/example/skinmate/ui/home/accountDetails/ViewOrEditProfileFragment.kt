package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FragmentViewEditProfileBinding
import com.example.skinmate.ui.auth.AuthViewModel
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.SignUpFragment
import com.example.skinmate.ui.home.HomeViewModel

class ViewOrEditProfileFragment : BaseFragment() {

    lateinit var viewEditProfileBinding: FragmentViewEditProfileBinding
    private val viewModel by viewModels<HomeViewModel>()
    var mailingaddress : String?=null
    var insuranceinfo : String?=null
    var emergencycontactname : String?=null
    var emergencyphonenumber : String?=null

    companion object {
        fun newInstance() = ViewOrEditProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")
        viewEditProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_edit_profile,container,false)

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
        val email = sharedPref!!.getString(SignUpFragment.EMAIL_ID,"none")

        viewEditProfileBinding.etFirstName.setEnabled(false)
        viewEditProfileBinding.etLastName.setEnabled(false)
        viewEditProfileBinding.cardMale.setClickable(false)
        viewEditProfileBinding.cardFemale.setClickable(false)
        viewEditProfileBinding.cardOther.setClickable(false)
        viewEditProfileBinding.autocompleteBloodGrp.setEnabled(false)
        viewEditProfileBinding.etDob.setEnabled(false)


        viewModel.getCustomerDetails("Bearer $token",custId!!).observe(requireActivity()){
            Log.d("profile",it.get(0).responseInformation.get(0).firstName)

            if(it.get(0).responseMessage){
                viewEditProfileBinding.etFirstName.setText(it.get(0).responseInformation.get(0).firstName)
                viewEditProfileBinding.etLastName.setText(it.get(0).responseInformation.get(0).lastName)
                viewEditProfileBinding.etDob.setText(it.get(0).responseInformation.get(0).dob.date)
                viewEditProfileBinding.etEmergencyContactName.setText(it.get(0).responseInformation.get(0).emeregencyContactName)
                viewEditProfileBinding.etEmergencyContactNumber.setText(it.get(0).responseInformation.get(0).emeregencyNumber)
                viewEditProfileBinding.etMailingAddress.setText(it.get(0).responseInformation.get(0).address)
                viewEditProfileBinding.etInsuranceInfo.setText(it.get(0).responseInformation.get(0).insuranceInformation)
                if (it[0].responseInformation.get(0).gender == 1){
                    viewEditProfileBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                    viewEditProfileBinding.ImageViewSelectedGenderMale.isVisible=true
                } else if (it[0].responseInformation.get(0).gender== 2){
                    viewEditProfileBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                    viewEditProfileBinding.ImageViewSelectedGenderFemale.isVisible=true
                } else {
                    viewEditProfileBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                    viewEditProfileBinding.ImageViewSelectedGenderOther.isVisible=true
                }
            }
        }

        viewEditProfileBinding.btnSaveChanges.setOnClickListener {

            mailingaddress = viewEditProfileBinding.etMailingAddress.text.toString()
            insuranceinfo = viewEditProfileBinding.etInsuranceInfo.text.toString()
            emergencycontactname = viewEditProfileBinding.etEmergencyContactName.text.toString()
            emergencyphonenumber = viewEditProfileBinding.etEmergencyContactNumber.text.toString()

            viewModel.postEditCustomer("Bearer $token",custId!!,mailingaddress!!,email!!,insuranceinfo!!,emergencycontactname!!,emergencyphonenumber!!).observe(requireActivity()){
                if(it.get(0).Code == 200) {
                    Toast.makeText(requireContext(),"Changes updated",Toast.LENGTH_LONG).show()
                }
            }
        }

        return viewEditProfileBinding.root
    }
}