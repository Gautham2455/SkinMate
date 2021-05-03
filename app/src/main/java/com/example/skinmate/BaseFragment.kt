package com.example.dummyproject

import androidx.fragment.app.DialogFragment

abstract class BaseFragment : DialogFragment() {

    fun add(containerId: Int, fragment: BaseFragment) {
        activity?.run {
            (this as BaseActivity).add(containerId, fragment)
        }
    }

    fun add(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean) {
        activity?.run {
            (this as BaseActivity).add(containerId, fragment, addToBackStack)
        }
    }

    fun replace(containerId: Int, fragment: BaseFragment) {
        activity?.run {
            (this as BaseActivity).replace(containerId, fragment)
        }
    }

    fun replace(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean) {
        activity?.run {
            (this as BaseActivity).replace(containerId, fragment, addToBackStack)
        }
    }

}