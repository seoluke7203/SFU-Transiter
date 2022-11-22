package com.example.sfutransiter.views.components

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {
    protected fun addFragment(
        viewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        addToBackstack: Boolean = true
    ) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction().add(viewId, fragment, fragmentTag)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragmentTag)
        }
        fragmentTransaction.commit()
    }

    protected fun replaceFragment(
        viewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        addToBackstack: Boolean = true
    ) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction().replace(viewId, fragment, fragmentTag)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragmentTag)
        }
        fragmentTransaction.commit()
    }
}