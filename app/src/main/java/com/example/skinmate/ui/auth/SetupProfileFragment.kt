package com.example.skinmate.ui.auth

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
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
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

class SetupProfileFragment : BaseFragment() {

    private val inputValidation=InputValidation()
    var PERMISSION_ID = 1000
    private lateinit var enterDetailsBinding: EnterDetailsBinding
    private var fusedLocationClient: FusedLocationProviderClient? = null
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
    var currentLocation : String? = null


    companion object {
        fun newInstance() = SetupProfileFragment()
        const val FIRSTNAME:String="firstname"
        const val LASTNAME:String="lastname"
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")
        enterDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.enter_details,container,false)
        val inputval= InputValidation()
        val c=Calendar.getInstance()
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        var bloodgroups=resources.getStringArray(R.array.Bloodgroup)
        var arrayAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_menu,bloodgroups)
        enterDetailsBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        enterDetailsBinding.cardMale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderMale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=true

                enterDetailsBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                gender = enterDetailsBinding.tvGenderMale.text.toString()

            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            }

        }

        enterDetailsBinding.cardFemale.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=true

                enterDetailsBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                gender = enterDetailsBinding.tvGenderFemale.text.toString()

            }
            else {
            enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

        }
        enterDetailsBinding.cardOther.setOnClickListener {
            if(!enterDetailsBinding.ImageViewSelectedGenderOther.isVisible) {
                enterDetailsBinding.ImageViewSelectedGenderMale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderFemale.isVisible=false
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=true
                enterDetailsBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                gender = enterDetailsBinding.tvGenderOther.text.toString()

            }
            else {
                enterDetailsBinding.ImageViewSelectedGenderOther.isVisible=false
                enterDetailsBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
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
            enterDetailsBinding.etMailingAddress.setText(getLastLocation())

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

                val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPref!!.edit()
                editor.putString(SetupProfileFragment.FIRSTNAME,firstname)
                editor.putString(SetupProfileFragment.LASTNAME,lastname)
                editor.apply()
                editor.commit()
                val jsonObject=JSONObject()

                jsonObject.put("phoneNumber",sharedPref!!.getString(SignUpFragment.MOB_NO!!,"00"))
                jsonObject.put("email", sharedPref!!.getString(SignUpFragment.EMAIL_ID!!,"none"))
                jsonObject.put("firstName",firstname)
                jsonObject.put("lastName",lastname)
                jsonObject.put("gender",gender)
                jsonObject.put("dob",dateofbirth)
                jsonObject.put("bloodGroup",bloodgroup_user)
                jsonObject.put("loginType","Android")
                jsonObject.put("password",sharedPref!!.getString(SignUpFragment.USER_PASSWORD!!,"none"))
                jsonObject.put("address",mailingaddress)
                jsonObject.put("emeregencyNumber",emergencyphonenumber)
                jsonObject.put("insuranceInformation",insuranceinfo)
                jsonObject.put("emeregencyContactName",emergencycontactname)
                val jsonObjectString = jsonObject.toString()

                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                viewModel.postRegisterUser(requestBody).observe(requireActivity()){
                    if(it.get(0).responseMessage!!)
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                }
            }

        }

        enterDetailsBinding.etEmergencyContactNumber.addTextChangedListener(textWatcher)

        return enterDetailsBinding.root
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

    private val textWatcher=object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone_no: String=enterDetailsBinding.etEmergencyContactNumber.text.toString()

            enterDetailsBinding.EmergencyNumberLayout.error = if (inputValidation.isPhoneValid(phone_no)) null else "Enter Valid Phone Number"

        }

    }

}