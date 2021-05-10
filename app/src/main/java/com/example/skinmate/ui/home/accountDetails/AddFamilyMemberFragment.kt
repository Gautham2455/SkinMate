package com.example.skinmate.ui.home.accountDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FamilyMembersOptionBinding
import com.example.skinmate.databinding.FragmentAddFamilyMemberSetupProfileBinding

class AddFamilyMemberFragment : BaseFragment() {

    lateinit var addFamilyMemberSetupProfileBinding: FragmentAddFamilyMemberSetupProfileBinding

    companion object{
        fun newInstance() = AddFamilyMemberFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setTitleWithBackButton("Setup Profile")

        addFamilyMemberSetupProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_family_member_setup_profile,container,false)



        return addFamilyMemberSetupProfileBinding.root
    }
}