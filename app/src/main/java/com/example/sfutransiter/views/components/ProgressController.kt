package com.example.sfutransiter.views.components

import android.view.View

class ProgressController(private val view: View, private val progressView: View) {
    fun start() {
        view.visibility = View.GONE
        progressView.visibility = View.VISIBLE
    }

    fun end(): Unit {
        view.visibility = View.VISIBLE
        progressView.visibility = View.GONE
    }
}