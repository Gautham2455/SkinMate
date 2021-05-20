package com.example.skinmate.ui.home.accountDetails

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FragmentEditFamilyMemberDetailsBinding
import com.example.skinmate.ui.auth.AuthViewModel
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.SignUpFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.StringToValue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

class EditFamilyMemberDetailsFragment : BaseFragment() {

    lateinit var editFamilyMemberDetailsBinding: FragmentEditFamilyMemberDetailsBinding
    private val inputValidation= InputValidation()
    var PERMISSION_ID = 1000
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private val viewModel by viewModels<HomeViewModel>()
    private val stringTovalue = StringToValue()

    var firstname : String?=null
    var lastname : String?=null
    var dateofbirth : String?=null
    var mailingaddress : String?=null
    var insuranceinfo : String?=null
    var emergencycontactname : String?=null
    var emergencyphonenumber : String?=null
    var bloodgroup_user : String?=null
    var gender :String?= null
    var genderId : String?=null
    var currentLocation : String? = null
    var relationshiptype : String?=null
    var relationshipid : String?=null

    companion object {
        fun newInstance() = EditFamilyMemberDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editFamilyMemberDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_family_member_details,
            container,
            false
        )
        
        HomeActivity.bottomNavigationView.visibility=View.GONE

        val inputval= InputValidation()
        val c= Calendar.getInstance()
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        var bloodgroups=resources.getStringArray(R.array.Bloodgroup)
        var arrayAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_menu,bloodgroups)
        editFamilyMemberDetailsBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
        val familyProfileId = FamilyMemberListFragment.familyProfileId.toString()

        editFamilyMemberDetailsBinding.etFirstName.setEnabled(false)
        editFamilyMemberDetailsBinding.etLastName.setEnabled(false)
        editFamilyMemberDetailsBinding.cardMale.setClickable(false)
        editFamilyMemberDetailsBinding.cardFemale.setClickable(false)
        editFamilyMemberDetailsBinding.cardOther.setClickable(false)
        editFamilyMemberDetailsBinding.autocompleteBloodGrp.setEnabled(false)
        editFamilyMemberDetailsBinding.etDob.setEnabled(false)


      viewModel.getFamilyMember("Bearer $token",familyProfileId).observe(requireActivity()){
          Log.v("Edit",it.get(0).responseMessage.toString())
          if(it.get(0).responseMessage){
              editFamilyMemberDetailsBinding.etFirstName.setText(it.get(0).responseInformation.firstName)
              Log.v("Firstname",it.get(0).responseInformation.firstName)
              editFamilyMemberDetailsBinding.etLastName.setText(it.get(0).responseInformation.lastName)
              editFamilyMemberDetailsBinding.etDob.setText(it.get(0).responseInformation.dob)
              editFamilyMemberDetailsBinding.etEmergencyContactName.setText(it.get(0).responseInformation.emeregencyContactName)
              editFamilyMemberDetailsBinding.etEmergencyContactNumber.setText(it.get(0).responseInformation.emeregencyNumber)
              editFamilyMemberDetailsBinding.etMailingAddress.setText(it.get(0).responseInformation.address)
              editFamilyMemberDetailsBinding.etInsuranceInfo.setText(it.get(0).responseInformation.insuranceInformation)
              editFamilyMemberDetailsBinding.etRelationshipType.setText(stringTovalue.relationIdToType(it.get(0).responseInformation.relationshipId))
              if (it[0].responseInformation.gender == 1){
                  editFamilyMemberDetailsBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                  editFamilyMemberDetailsBinding.ImageViewSelectedGenderMale.isVisible=true
              } else if (it[0].responseInformation.gender== 2){
                  editFamilyMemberDetailsBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                  editFamilyMemberDetailsBinding.ImageViewSelectedGenderFemale.isVisible=true
              } else {
                  editFamilyMemberDetailsBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                  editFamilyMemberDetailsBinding.ImageViewSelectedGenderOther.isVisible=true
              }


          }
      }





        editFamilyMemberDetailsBinding.tvCurrentLocation.setOnClickListener {if (!CheckPermission()) {

            RequestPermission()
        }
        else {
            editFamilyMemberDetailsBinding.etMailingAddress.setText(getLastLocation())

        }}

        editFamilyMemberDetailsBinding.btnSaveChanges.setOnClickListener {

            mailingaddress=editFamilyMemberDetailsBinding.etMailingAddress.text.toString()
            insuranceinfo=editFamilyMemberDetailsBinding.etInsuranceInfo.text.toString()
            emergencycontactname=editFamilyMemberDetailsBinding.etEmergencyContactName.text.toString()
            emergencyphonenumber=editFamilyMemberDetailsBinding.etEmergencyContactNumber.text.toString()
            bloodgroup_user=editFamilyMemberDetailsBinding.autocompleteBloodGrp.text.toString()


            if (!inputval.isPhoneValid(emergencyphonenumber!!)) {
                editFamilyMemberDetailsBinding.EmergencyNumberLayout.setError("Please Enter a valid Phone number")
            }
            else {

                val jsonObject= JSONObject()
                jsonObject.put("address",mailingaddress)
                jsonObject.put("emeregencyNumber",emergencyphonenumber)
                jsonObject.put("insuranceInformation",insuranceinfo)
                jsonObject.put("emeregencyContactName",emergencycontactname)
                val jsonObjectString = jsonObject.toString()

                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                viewModel.putFamilyMemberEdidtDetails("Bearer $token",familyProfileId,requestBody).observe(requireActivity()){
                    if(it.get(0).responseMessage==true) {
                        replace(R.id.fragment_container,FamilyMemberListFragment.newInstance())
                        Toast.makeText(
                            requireContext(),
                            "Changes Updated Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        return editFamilyMemberDetailsBinding.root
    }

    private fun getLastLocation() : String? {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                currentLocation= getCurrentLocation((lastLocation)!!.latitude,(lastLocation)!!.longitude)
                Log.d("location address",currentLocation.toString())
            }
        }
        return currentLocation
    }


    fun RequestPermission(){
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID)
    }

    fun CheckPermission():Boolean{

        if(
            context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED ||
            context?.let { ActivityCompat.checkSelfPermission(it,android.Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }



    private fun getCurrentLocation(lat: Double,long: Double):String {
        var cityName: String = ""
        var countryName = ""
        var address = ""
        var geoCoder = Geocoder(requireContext(), Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat, long, 3)

        address = Adress.get(0).getAddressLine(0)
        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        currentLocation = address + cityName + countryName
        Log.d("Debug:", "Your City: " + cityName + " ; your Country " + countryName)
        return currentLocation!!
    }

    private val textWatcher=object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String=editFamilyMemberDetailsBinding.etEmergencyContactNumber.text.toString()

            editFamilyMemberDetailsBinding.EmergencyNumberLayout.error = if (inputValidation.isPhoneValid(phone_no)) null else "Enter Valid Phone Number"

        }

    }

}