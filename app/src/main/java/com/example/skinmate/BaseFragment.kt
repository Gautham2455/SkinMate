package com.example.skinmate

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFragment : DialogFragment() {

    val toolbar: Toolbar?
        get() {
            var toolbar: Toolbar? = null
            activity?.run {
                toolbar = (this as BaseActivity).mainToolbar
            }
            return toolbar
        }

    fun setTitle(resMessage: Int){
        with(activity as BaseActivity){
            setTitle(resMessage)
            setBackDisabled()
        }
    }

    fun setTitle(message: String?){
        with(activity as BaseActivity){
            setTitle(message)
            setBackDisabled()
        }
    }

    fun setTitleWithBackButton(resMessage: Int){
        (activity as BaseActivity).showMainToolbar(resMessage)
    }

    fun setTitleWithBackButton(message: String?){
        (activity as BaseActivity).showMainToolbar(message)
    }

    open fun onBackPress() {
       //do nothing
    }

    open fun onHomeButtonPress() {
        //do nothing
    }

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

    fun showbottomsheet(fragment: BottomSheetDialogFragment){
        activity?.run{
            (this as BaseActivity).showbottomsheet(fragment)
        }
    }

}