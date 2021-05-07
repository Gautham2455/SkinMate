package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.skinmate.BaseFragment
import com.example.skinmate.R

class AuthenticationFragment :BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        val view = inflater.inflate(R.layout.fragment_authentication, container, false)
        view.findViewById<Button>(R.id.btn_signup).setOnClickListener {
            replace(R.id.fragment_container,SignUpFragment.newInstance())
        }
        view.findViewById<Button>(R.id.btn_signin).setOnClickListener {
            replace(R.id.fragment_container,SignInFragment.newInstance())
        }
        return view
    }

    companion object{
        fun newInstance()=AuthenticationFragment()
    }
}