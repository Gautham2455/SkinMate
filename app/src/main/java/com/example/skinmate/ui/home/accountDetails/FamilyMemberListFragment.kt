package com.example.skinmate.ui.home.accountDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FamilyMembersOptionBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.home.HomeViewModel

class FamilyMemberListFragment : BaseFragment() {

    lateinit var familyMembersOptionBinding: FamilyMembersOptionBinding
    private val viewModel by viewModels<HomeViewModel>()

    companion object{
        fun newInstance() = FamilyMemberListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Family Members")

        familyMembersOptionBinding = DataBindingUtil.inflate(inflater, R.layout.family_members_option,container,false)


        val sharedPref: SharedPreferences =requireActivity()!!.getSharedPreferences("SkinMate",
            Context.MODE_PRIVATE)
        val custId=sharedPref!!.getString(SignInFragment.CUSTOMER_ID,"none")
        val token=sharedPref!!.getString(SignInFragment.TOKEN,"none")


        viewModel.getFamilyMembersList("Bearer $token",custId!!).observe(requireActivity()){
            familyList ->
            Log.d("family list",familyList[0].responseInformation[0].firstName)
        }

        familyMembersOptionBinding.fab.setOnClickListener { replace(R.id.fragment_container,AddFamilyMemberFragment.newInstance()) }


        return familyMembersOptionBinding.root
    }
}