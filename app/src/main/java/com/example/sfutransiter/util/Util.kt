package com.example.sfutransiter.util

import android.Manifest.permission.INTERNET
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Generic static object to use across the program
 * */
object Util {
    const val PERM_REQUEST_CODE = 0

    fun checkPermissions(activity: Activity?) {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(INTERNET),
                PERM_REQUEST_CODE
            )
        }
    }
}