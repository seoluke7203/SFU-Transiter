package com.example.sfutransiter.views.components

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sfutransiter.R

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
            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.enter_fade_in,   // new fragment entering
                R.anim.scale,    // existing fragment
                R.anim.enter_fade_in,    // remaining fragment when exiting
                R.anim.exit_fade_out
            ).replace(viewId, fragment, fragmentTag)
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragmentTag)
        }
        fragmentTransaction.commit()
    }

    protected fun popBackStack() {
        supportFragmentManager.popBackStack()
    }
}