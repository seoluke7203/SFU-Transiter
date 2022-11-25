package com.example.sfutransiter.repository

import android.util.Log
import com.example.sfutransiter.backend.RetrofitInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

/**
 * Class for communication between AWS API and our model.
 */
class AWSRepo(retrofit: Retrofit) : Repository() {
    private val aws = retrofit.create(RetrofitInterface.AWS::class.java)

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