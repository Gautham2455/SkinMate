package com.example.skinmate.ui.home.accountDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FamilyMembersOptionBinding

class FamilyMemberListFragment : BaseFragment() {

    lateinit var familyMembersOptionBinding: FamilyMembersOptionBinding

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

        familyMembersOptionBinding.fab.setOnClickListener { replace(R.id.fragment_container,AddFamilyMemberFragment.newInstance()) }


        return familyMembersOptionBinding.root
    }
}