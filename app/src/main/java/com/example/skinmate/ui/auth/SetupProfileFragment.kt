package com.example.skinmate.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.EnterDetailsBinding
import com.example.skinmate.ui.home.HomeActivity
import com.google.android.gms.location.*
import androidx.lifecycle.observe
import java.util.*

class SetupProfileFragment : BaseFragment() {


    var PERMISSION_ID = 1000
    lateinit var enterDetailsBinding: EnterDetailsBinding
    lateinit var locationRequest: LocationRequest
    var currentLocation : String ?= null

    private var fusedLocationClient: FusedLocationProviderClient? = null

    //private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null


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
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#B2BFB8"))


                gender = enterDetailsBinding.tvGenderMale.text.toString()

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
                gender = enterDetailsBinding.tvGenderFemale.text.toString()

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
                gender = enterDetailsBinding.tvGenderOther.text.toString()

            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.cardFemale.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
        enterDetailsBinding.etDob.setOnClickListener {
            var dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                var mmMonth = mMonth+1
                dateofbirth = "$mDay-$mmMonth-$mYear"
                enterDetailsBinding.etDob.setText(dateofbirth)
            },year,month,day)
            dpd.show()

        }

        enterDetailsBinding.tvCurrentLocation.setOnClickListener {if (!CheckPermission()) {

            RequestPermission()
        }
        else {
            getLastLocation()
        }}


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
                gender!!,dateofbirth!!,bloodgroup_user!!,"Android",sharedPref!!.getString(SignUpFragment.USER_PASSWORD!!,"none")!!,
                mailingaddress!!,emergencyphonenumber.toString()!!.toInt(),insuranceinfo!!,emergencycontactname!!).observe(requireActivity()){
                    if(it.get(0).responseMessage!!)
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
            }

        }

        return enterDetailsBinding.root
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

    private fun getLastLocation() {
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
            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                currentLocation = getCurrentLocation((lastLocation)!!.latitude, (lastLocation)!!.longitude)
                enterDetailsBinding.etMailingAddress.setText(currentLocation)
                Log.d("Location",currentLocation.toString())
            }
            else {
//                Log.w(TAG, "getLastLocation:exception", task.exception)
//                ("No location detected. Make sure location is enabled on the device.")
            }
        }
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
}