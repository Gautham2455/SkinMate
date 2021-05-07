package com.example.skinmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseActivity : AppCompatActivity() {

    var mainToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //remove everything from back-stack. For some phone if screen is in background again new creation of activity will be occurred
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

    fun setActionBar(resId: Int) {
        mainToolbar = findViewById(resId)
        mainToolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            toolbar.setNavigationOnClickListener {
                val fragment = supportFragmentManager.findFragmentByTag(
                    (supportFragmentManager.backStackEntryCount - 1).toString()
                )
                if (fragment is BaseFragment) {
                    fragment.onHomeButtonPress()
                }
                onBackPressed()
            }
        }
    }

    fun setIcon(res: Int) {
        mainToolbar?.setNavigationIcon(res)
    }

    fun clearNavigationIcon() {
        mainToolbar?.navigationIcon = null
    }

    fun setTitle(title: String) {
        val actionBar = supportActionBar
        actionBar?.run {
            this.title = title
            setDisplayShowTitleEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun setTitle(resId: Int) {
        setTitle(getString(resId))
    }

    fun showMainToolbar(resource: String?) {
        // We have to every time because hide is actually will remove the Toolbar.
        setSupportActionBar(mainToolbar)
        supportActionBar?.run {
            show()
            setBackEnabled()
            title = resource
            setHomeAsUpIndicator(R.drawable.backbutton)
            setDisplayShowTitleEnabled(true)
        }
    }

    fun showMainToolbar(resourceId: Int) {
        showMainToolbar(getString(resourceId))
    }


    @JvmOverloads
    fun add(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean = true) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(containerId, fragment ,Integer.toString(supportFragmentManager.backStackEntryCount))
        if (addToBackStack) ft.addToBackStack(null)
        ft.commit()
    }

    @JvmOverloads
    fun replace(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean = true) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(containerId, fragment, Integer.toString(supportFragmentManager.backStackEntryCount))
        if (addToBackStack) ft.addToBackStack(null)
        ft.commit()
        fm.executePendingTransactions()

    }

    fun showbottomsheet(fragment: BottomSheetDialogFragment){
        fragment.show(supportFragmentManager, "BottomSheet")
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            supportFragmentManager.findFragmentByTag((count - 1).toString())?.run {
                (this as BaseFragment).onBackPress()
            }
        }
    }

    fun setBackEnabled() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    fun setBackDisabled() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }
}