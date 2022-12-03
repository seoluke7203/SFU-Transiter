package com.example.sfutransiter.model.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sfutransiter.model.BusStopReview
import com.example.sfutransiter.repository.AWSRepo
import retrofit2.Response

class BusReviewViewModel(private val repository: AWSRepo) : ViewModel() {
    // TODO implement APIs
    // private var ratings = MutableLiveData<>()
    private var busStopReview = MutableLiveData<Response<BusStopReview.Response>>()
    private var busStopReviews = MutableLiveData<Response<BusStopReview.ResponseList>>()

    fun insertBusStopReview(
        stopNo: String,
        body: BusStopReview.Request
    ): LiveData<Response<BusStopReview.Response>> {
        busStopReview = repository.insertBusStopReview(stopNo, body)
        return busStopReview
    }

    fun updateBusStopReview(
        stopNo: String,
        stopReviewRn: String,
        body: BusStopReview.Request
    ): LiveData<Response<BusStopReview.Response>> {
        busStopReview = repository.updateBusStopReview(stopNo, stopReviewRn, body)
        return busStopReview
    }

    fun listBusStopReviews(
        stopNo: String,
    ): LiveData<Response<BusStopReview.ResponseList>> {
        busStopReviews = repository.listBusStopReviews(stopNo)
        return busStopReviews
    }

    fun ping() {
        repository.ping()
    }
}