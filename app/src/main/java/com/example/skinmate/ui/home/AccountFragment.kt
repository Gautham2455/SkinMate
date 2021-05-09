package com.example.skinmate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ProfileMenuBinding
import com.example.skinmate.ui.auth.WelcomeActivity

class AccountFragment : BaseFragment() {

    private lateinit var profileMenuBinding: ProfileMenuBinding

    companion object {
        fun newInstance() = AccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        profileMenuBinding = DataBindingUtil.inflate(inflater,R.layout.profile_menu,container,false)

        profileMenuBinding.logoutBtn.setOnClickListener {
            activity?.finish()
            startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
        }

        return profileMenuBinding.root
    }
}