package com.example.skinmate

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseActivity : AppCompatActivity() {

    var mainToolbar: Toolbar? = null

    @JvmOverloads
    fun add(containerId: Int, fragment: BaseFragment, addToBackStack: Boolean = true) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(containerId, fragment, Integer.toString(supportFragmentManager.backStackEntryCount))
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

    fun setActionBar(resId: Int) {
        mainToolbar = findViewById(resId)
        mainToolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            toolbar.setNavigationOnClickListener {
                val fragment = supportFragmentManager.findFragmentByTag(
                    (supportFragmentManager.backStackEntryCount - 1).toString()
                )
                onBackPressed()
            }
        }
    }

    fun setIcon(res: Int) {
        mainToolbar?.setNavigationIcon(res)
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
}