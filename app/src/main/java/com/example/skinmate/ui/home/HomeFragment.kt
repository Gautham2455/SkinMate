package com.example.skinmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.LadingScreenBinding


class HomeFragment : BaseFragment() {

    private lateinit var ladingScreenBinding: LadingScreenBinding


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()

        ladingScreenBinding=DataBindingUtil.inflate(inflater,R.layout.lading_screen,container,false)


        return ladingScreenBinding.root

    }
}