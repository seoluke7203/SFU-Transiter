package com.example.sfutransiter.model.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.repository.Repository
import com.example.sfutransiter.repository.TLinkRepo

class MyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TransitViewModel::class.java) && repository is TLinkRepo)
            return TransitViewModel(repository) as T
        else if (modelClass.isAssignableFrom(BusReviewViewModel::class.java) && repository is AWSRepo)
            return BusReviewViewModel(repository) as T
        else if (modelClass.isAssignableFrom(UserViewModel::class.java) && repository is AWSRepo)
            return UserViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}