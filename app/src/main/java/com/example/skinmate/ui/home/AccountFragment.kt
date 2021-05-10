package com.example.skinmate.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.ProfileMenuBinding
import com.example.skinmate.ui.auth.SignInFragment
import com.example.skinmate.ui.auth.WelcomeActivity
import com.example.skinmate.ui.home.accountDetails.ChangePwdFragment

class AccountFragment : BaseFragment() {

    private lateinit var profileMenuBinding: ProfileMenuBinding

    companion object {
        fun newInstance() = AccountFragment()
        const val TOUCH_ID:String="touch_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        profileMenuBinding = DataBindingUtil.inflate(inflater,R.layout.profile_menu,container,false)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        profileMenuBinding.tvTouchId.setChecked(sharedPref!!.getBoolean(AccountFragment.TOUCH_ID,false))


        profileMenuBinding.changePasswordCard.setOnClickListener { replace(R.id.fragment_container,ChangePwdFragment.newInstance()) }

        profileMenuBinding.tvTouchId.setOnCheckedChangeListener { buttonView, isChecked ->

            val editor: SharedPreferences.Editor = sharedPref!!.edit()
            if(isChecked) {
                editor.putBoolean(AccountFragment.TOUCH_ID, isChecked)
                editor.commit()
            }
            if(!isChecked){
                editor.putBoolean(AccountFragment.TOUCH_ID, isChecked)
                editor.commit()
            }
        }

        profileMenuBinding.logoutBtn.setOnClickListener {
            activity?.finish()
            startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
        }

        return profileMenuBinding.root
    }
}