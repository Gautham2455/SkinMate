package com.example.skinmate.ui.home.accountDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.FragmentViewEditProfileBinding

class ViewOrEditProfileFragment : BaseFragment() {

    lateinit var viewEditProfileBinding: FragmentViewEditProfileBinding

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

        return viewEditProfileBinding.root
    }
}