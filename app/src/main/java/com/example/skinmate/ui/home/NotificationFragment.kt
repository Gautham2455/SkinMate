package com.example.skinmate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skinmate.BaseFragment
import com.example.skinmate.R

class NotificationFragment : BaseFragment() {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        return view

    }
}