package com.example.skinmate.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.skinmate.BaseFragment
import com.example.skinmate.R
import com.example.skinmate.databinding.SuccessMsgBinding

class SuccessMessageFragment: BaseFragment() {
    private lateinit var successMsgBinding: SuccessMsgBinding

    companion object{
        fun newInstance()=SuccessMessageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        successMsgBinding=DataBindingUtil.inflate(inflater,R.layout.success_msg,container,false)

        successMsgBinding.btnContinueToLogin.setOnClickListener {
            add(R.id.fragment_container,SignInFragment.newInstance())
        }

        return successMsgBinding.root
    }

}