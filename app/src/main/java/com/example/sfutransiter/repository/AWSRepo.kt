package com.example.sfutransiter.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sfutransiter.backend.RetrofitInterface
import com.example.sfutransiter.model.BusStopReview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Class for communication between AWS API and our model.
 */
class AWSRepo(retrofit: Retrofit) : Repository() {
    private val aws = retrofit.create(RetrofitInterface.AWS::class.java)

    /**
     * Insert a bus stop review from a user or anonymous user into the DB
     * @param stopNo the stop identifier (5 digits)
     * @param body the body of the request to send
     */
    fun insertBusStopReview(
        stopNo: String,
        body: BusStopReview.Request
    ): MutableLiveData<Response<BusStopReview.Response>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.insertBusStopReview(stopNo, body)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "insertBusStopReview: $response")
                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "insertBusStopReview: Failed, $e")
            }
        }
        return stopReviewLiveData
    }

    /**
     * Updates a bus stop review from a user or anonymous user into the DB
     * @param stopNo the stop identifier (5 digits)
     * @param stopReviewRn the stop review resource name to update
     * @param body the body of the request to send
     */
    fun updateBusStopReview(
        stopNo: String,
        stopReviewRn: String,
        body: BusStopReview.Request
    ): MutableLiveData<Response<BusStopReview.Response>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.updateBusStopReview(stopNo, stopReviewRn, body)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "updateBusStopReview: $response")
                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "updateBusStopReview: Failed, $e")
            }
        }
        return stopReviewLiveData
    }

    /**
     * Gets a list of bus stop reviews
     * @param stopNo the stop identifier (5 digits)
     */
    fun listBusStopReviews(
        stopNo: String,
    ): MutableLiveData<Response<BusStopReview.ResponseList>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.ResponseList>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.listBusStopReviews(stopNo)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "listBusStopReview: $response")
                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "listBusStopReview: Failed, $e")
            }
        }
        return stopReviewLiveData
    }

    /**
     * Pings the connection of AWS server
     */
    fun ping() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.ping()
                Log.i(AWSRepo::class.java.simpleName, "ping: response $response")
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "ping: Failed, $e")
            }
        }
    }
}