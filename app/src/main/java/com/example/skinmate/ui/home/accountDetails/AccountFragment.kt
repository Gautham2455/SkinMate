package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ProfileMenuBinding
import com.example.skinmate.ui.auth.SetupProfileFragment
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.WelcomeActivity
import com.example.skinmate.ui.home.HomeActivity
import com.example.skinmate.ui.home.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AccountFragment : BaseFragment() {

    private lateinit var profileMenuBinding: ProfileMenuBinding
    private val viewModel by viewModels<HomeViewModel>()


    companion object {
        fun newInstance() =
            AccountFragment()
        var touch_id: Boolean=false
        var TOUCH_ID:String=" "
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        profileMenuBinding = DataBindingUtil.inflate(inflater,R.layout.profile_menu,container,false)
        HomeActivity.bottomNavigationView.visibility = View.VISIBLE

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")
        touch_id = sharedPref!!.getString(TOUCH_ID,"none").toBoolean()
        profileMenuBinding.tvTouchId.setChecked(touch_id)



        profileMenuBinding.profileInfoCard.setOnClickListener { replace(R.id.fragment_container,ViewOrEditProfileFragment.newInstance()) }

        profileMenuBinding.familyCard.setOnClickListener {
            replace(R.id.fragment_container,FamilyMemberListFragment.newInstance()) }

        profileMenuBinding.insuranceCard.setOnClickListener { replace(R.id.fragment_container,AddInsuranceFragment.newInstance()) }

        profileMenuBinding.changePasswordCard.setOnClickListener { replace(R.id.fragment_container,ChangePwdFragment.newInstance()) }

        profileMenuBinding.tvTouchId.setOnCheckedChangeListener { buttonView, isChecked ->
            val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPref!!.edit()
            if(isChecked) {

                editor.putString(TOUCH_ID, "true")
                editor.apply()
                editor.commit()
                Log.d("touchid2", TOUCH_ID)
            }
            if(!isChecked){

                editor.putString(TOUCH_ID, "false")
                editor.apply()
                editor.commit()
            }


        }

        profileMenuBinding.logoutBtn.setOnClickListener {
            activity?.finish()
            startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
        }

        return profileMenuBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")

        viewModel.getCustomerDetails("Bearer $token",custId!!).observe(this){
            if(it.get(0).responseMessage)
                profileMenuBinding.userName.setText(it.get(0).responseInformation.get(0).firstName +" " + it.get(0).responseInformation.get(0).lastName)
            Log.v("name",it.get(0).responseInformation.get(0).firstName +" " + it.get(0).responseInformation.get(0).lastName)
        }

    }
}