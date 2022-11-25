package com.example.sfutransiter.model.view_model

import androidx.lifecycle.ViewModel
import com.example.sfutransiter.repository.AWSRepo

class BusViewModel(private val repository: AWSRepo) : ViewModel() {
    // TODO implement APIs
    // private var ratings = MutableLiveData<>()

    fun ping() {
        repository.ping()
    }
}