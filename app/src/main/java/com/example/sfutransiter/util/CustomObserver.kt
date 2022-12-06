package com.example.sfutransiter.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, callback: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                removeObserver(this)
                return
            }
            removeObserver(this)
            callback(value)
        }
    })
}