package com.example.skinmate.ui.home.accountDetails

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class ViewOrEditProfileFragment : BaseFragment() {

    lateinit var viewEditProfileBinding: FragmentViewEditProfileBinding
    private val viewModel by viewModels<HomeViewModel>()
    var mailingaddress : String?=null
    var insuranceinfo : String?=null
    var emergencycontactname : String?=null
    var emergencyphonenumber : String?=null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    var currentLocation : String? = null
    var PERMISSION_ID = 1000

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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

            val editor: SharedPreferences.Editor =  sharedPref!!.edit()
            editor.putString("UserFristName",it.get(0).responseInformation.get(0).firstName)
            editor.putString("UserLastName",it.get(0).responseInformation.get(0).lastName)
            editor.apply()
            editor.commit()

            if(it.get(0).responseMessage){
                viewEditProfileBinding.etFirstName.setText(it.get(0).responseInformation.get(0).firstName)
                viewEditProfileBinding.etLastName.setText(it.get(0).responseInformation.get(0).lastName)
                viewEditProfileBinding.etDob.setText(it.get(0).responseInformation.get(0).dob)
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
                    replace(R.id.fragment_container,AccountFragment.newInstance())
                    Toast.makeText(requireContext(),"Changes updated",Toast.LENGTH_LONG).show()
                }
            }
        }

       viewEditProfileBinding.tvCurrentLocation.setOnClickListener {if (!CheckPermission()) {

            RequestPermission()
        }
        else {
            viewEditProfileBinding.etMailingAddress.setText(getLastLocation())

        }}

        return viewEditProfileBinding.root
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
}