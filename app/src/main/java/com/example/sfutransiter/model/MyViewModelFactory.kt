package com.example.sfutransiter.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.repository.Repository

class MyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TransitViewModel::class.java))
            return TransitViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}