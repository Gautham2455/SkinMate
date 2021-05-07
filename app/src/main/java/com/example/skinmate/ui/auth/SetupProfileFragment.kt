package com.example.skinmate.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentProvider
import android.content.Intent
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
import com.example.sampleslinmate.utils.InputValidation
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.EnterDetailsBinding
import com.example.skinmate.ui.home.HomeActivity
import com.google.android.gms.location.*
import java.util.*

class SetupProfileFragment : BaseFragment() {

    var PERMISSION_ID = 1000
    lateinit var enterDetailsBinding: EnterDetailsBinding
    lateinit var locationRequest: LocationRequest
    var currentLocation : String = " "

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null


    companion object {
        fun newInstance() = SetupProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")
         enterDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.enter_details, container, false
        )

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())


        val inputval = InputValidation()
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val firstname = enterDetailsBinding.etFirstName.text.toString()
        val lastname = enterDetailsBinding.etLastName.text.toString()
        val dateofbirth = enterDetailsBinding.etDob.text.toString()
        val mailingaddress = enterDetailsBinding.etMailingAddress.text.toString()
        val insuranceinfo = enterDetailsBinding.etInsuranceInfo.text.toString()
        val emergencycontactname = enterDetailsBinding.etEmergencyContactName.text.toString()
        val emergencyphonenumber = enterDetailsBinding.etEmergencyContactNumber.text.toString()

        val bloodgroups = resources.getStringArray(R.array.Bloodgroup)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, bloodgroups)
        enterDetailsBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)

        val bloodgroup_user = enterDetailsBinding.autocompleteBloodGrp.text.toString()


        enterDetailsBinding.imageViewGenderMale.setOnClickListener {
            if (!enterDetailsBinding.ImageViewSelectedGenderMale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible = true
                val gender = enterDetailsBinding.tvGenderMale.text.toString()
            } else {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible = false
            }

        }

        enterDetailsBinding.imageViewGenderFemale.setOnClickListener {
            if (!enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible = true
                val gender = enterDetailsBinding.tvGenderFemale.text.toString()
            } else {
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible = false
            }

        }
        enterDetailsBinding.imageViewGenderOther.setOnClickListener {
            if (!enterDetailsBinding.ImageViewSelectedGenderOther.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible = false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible = true
                val gender = enterDetailsBinding.tvGenderOther.text.toString()
            } else {
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible = false
            }

        }
        enterDetailsBinding.etDob.setOnClickListener {
            var dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val mmMonth = mMonth + 1
                    val date = "$mDay/$mmMonth/$mYear"
                    enterDetailsBinding.etDob.setText(date)
                }, year, month, day
            )
            dpd.show()

        }

        enterDetailsBinding.tvCurrentLocation.setOnClickListener {if (!CheckPermission()) {

            RequestPermission()
        }
        else {
            getLastLocation()
        }}


        enterDetailsBinding.btnCreateMyAcnt.setOnClickListener {

            if (!inputval.isPhoneValid(emergencyphonenumber)) {
                enterDetailsBinding.EmergencyNumberLayout.setError("Please Enter a valid Phone number")

            } else {
                startActivity(Intent(context, HomeActivity::class.java))
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
                Log.d("Location",currentLocation)
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
        return currentLocation
    }
    }