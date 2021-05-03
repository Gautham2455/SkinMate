package com.example.dummyproject

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

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
}