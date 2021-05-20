package com.example.skinmate.ui.home.bookingAppointment

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
import com.example.skinmate.databinding.FragmentAddFamilyMemberSetupProfileBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.example.skinmate.utils.StringToValue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*

class AddMemberFragment : BaseFragment() {

    lateinit var addFamilyMemberSetupProfileBinding: FragmentAddFamilyMemberSetupProfileBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val inputValidation= InputValidation()
    private val stringTovalue = StringToValue()
    var PERMISSION_ID = 1000
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null


    var firstname : String?=null
    var lastname : String?=null
    var relationshiptype : String?=null
    var relationshipid : String?=null
    var dateofbirth : String?=null
    var mailingaddress : String?=null
    var insuranceinfo : String?=null
    var emergencycontactname : String?=null
    var emergencyphonenumber : String?=null
    var bloodgroup_user : String?=null
    var gender :String?= null
    var genderId : String?=null
    var currentLocation : String? = null

    companion object{
        fun newInstance() = AddMemberFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")

        addFamilyMemberSetupProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_family_member_setup_profile,container,false)
        HomeActivity.bottomNavigationView.visibility = View.GONE
        val inputval= InputValidation()
        val c= Calendar.getInstance()
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        var bloodgroups=resources.getStringArray(R.array.Bloodgroup)
        var arrayAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_menu,bloodgroups)
        addFamilyMemberSetupProfileBinding.autocompleteBloodGrp.setAdapter(arrayAdapter)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        addFamilyMemberSetupProfileBinding.etEmergencyContactNumber.addTextChangedListener(textWatcher)

        addFamilyMemberSetupProfileBinding.cardMale.setOnClickListener {
            if(!addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderMale.isVisible) {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderFemale.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderOther.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderMale.isVisible=true

                addFamilyMemberSetupProfileBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                gender = addFamilyMemberSetupProfileBinding.tvGenderMale.text.toString()

            }
            else {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderMale.isVisible=false
                addFamilyMemberSetupProfileBinding.cardMale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }

        addFamilyMemberSetupProfileBinding.cardFemale.setOnClickListener {
            if(!addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderFemale.isVisible) {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderMale.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderOther.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderFemale.isVisible=true

                addFamilyMemberSetupProfileBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))
                gender = addFamilyMemberSetupProfileBinding.tvGenderFemale.text.toString()

            }
            else {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderFemale.isVisible=false
                addFamilyMemberSetupProfileBinding.cardFemale.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }

        addFamilyMemberSetupProfileBinding.cardOther.setOnClickListener {
            if(!addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderOther.isVisible) {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderMale.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderFemale.isVisible=false
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderOther.isVisible=true
                addFamilyMemberSetupProfileBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.theme_background_light))

                gender = addFamilyMemberSetupProfileBinding.tvGenderOther.text.toString()

            }
            else {
                addFamilyMemberSetupProfileBinding.ImageViewSelectedGenderOther.isVisible=false
                addFamilyMemberSetupProfileBinding.cardOther.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }

        addFamilyMemberSetupProfileBinding.etDob.setOnClickListener {
            var dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    var mmMonth = mMonth+1
                    dateofbirth = "$mDay-$mmMonth-$mYear"
                    addFamilyMemberSetupProfileBinding.etDob.setText(dateofbirth)
                },year,month,day)
            dpd.show()
        }

        addFamilyMemberSetupProfileBinding.tvCurrentLocation.setOnClickListener {if (!CheckPermission()) {

            RequestPermission()
        }
        else {
            addFamilyMemberSetupProfileBinding.etMailingAddress.setText(getLastLocation())

        }}

        addFamilyMemberSetupProfileBinding.btnAddMember.setOnClickListener {
            firstname=addFamilyMemberSetupProfileBinding.etFirstName.text.toString()
            lastname=addFamilyMemberSetupProfileBinding.etLastName.text.toString()
            mailingaddress=addFamilyMemberSetupProfileBinding.etMailingAddress.text.toString()
            insuranceinfo=addFamilyMemberSetupProfileBinding.etInsuranceInfo.text.toString()
            emergencycontactname=addFamilyMemberSetupProfileBinding.etEmergencyContactName.text.toString()
            emergencyphonenumber=addFamilyMemberSetupProfileBinding.etEmergencyContactNumber.text.toString()
            bloodgroup_user=addFamilyMemberSetupProfileBinding.autocompleteBloodGrp.text.toString()
            relationshiptype =  addFamilyMemberSetupProfileBinding.etRelationshipType.text.toString()


            relationshipid = stringTovalue.relationTypeTorealtionId(relationshiptype!!)
            genderId = stringTovalue.genderToGenderId(gender!!)


            if (!inputval.isPhoneValid(emergencyphonenumber!!)) {
                addFamilyMemberSetupProfileBinding.EmergencyNumberLayout.setError("Please Enter a valid Phone number")
            }
            else{

                // call api to post data
                val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
                val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
                val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")


                viewModel.postAddFamilyMember("Bearer $token",custId!!,relationshipid!!,firstname!!,lastname!!,genderId!!,dateofbirth!!
                ,bloodgroup_user!!,mailingaddress!!,insuranceinfo!!,emergencycontactname!!,emergencyphonenumber!!).observe(requireActivity()){

                    if(it.get(0).Code == 200)
                        replace(R.id.fragment_container,AppointmentSummary.newInstance())

                    else
                        Toast.makeText(requireContext(),"Unsuccessfull",Toast.LENGTH_LONG).show()
                }


            }

        }



        return addFamilyMemberSetupProfileBinding.root
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
            val phone_no: String=addFamilyMemberSetupProfileBinding.etEmergencyContactNumber.text.toString()

            addFamilyMemberSetupProfileBinding.EmergencyNumberLayout.error = if (inputValidation.isPhoneValid(phone_no)) null else "Enter Valid Phone Number"

        }

    }

}