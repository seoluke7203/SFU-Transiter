package com.example.sfutransiter.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sfutransiter.backend.RetrofitInterface
import com.example.sfutransiter.model.BusStopReview
import com.example.sfutransiter.model.User
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
        body: BusStopReview.RequestBody
    ): MutableLiveData<Response<BusStopReview.Response>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.insertBusStopReview(stopNo, body)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "insertBusStopReview: $response, ${response.errorBody()!!.string()}")
//                }
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
        body: BusStopReview.RequestBody
    ): MutableLiveData<Response<BusStopReview.Response>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.updateBusStopReview(stopNo, stopReviewRn, body)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "updateBusStopReview: $response, ${response.errorBody()!!.string()}")
//                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "updateBusStopReview: Failed, $e")
            }
        }
        return stopReviewLiveData
    }

    /**
     * Deletes a bus stop review from a user
     * @param stopNo the stop identifier (5 digits)
     * @param stopReviewRn the stop review resource name to delete
     */
    fun deleteBusStopReview(
        stopNo: String,
        stopReviewRn: String,
    ): MutableLiveData<Response<BusStopReview.Response>> {
        val stopReviewLiveData = MutableLiveData<Response<BusStopReview.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.deleteBusStopReview(stopNo, stopReviewRn)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "deleteBusStopReview: $response, ${response.errorBody()!!.string()}")
//                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "deleteBusStopReview: Failed, $e")
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
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "listBusStopReview: $response, ${response.errorBody()!!.string()}")
//                }
                stopReviewLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "listBusStopReview: Failed, $e")
            }
        }
        return stopReviewLiveData
    }

    /***********************************************************************************************
     * USERS
     */

    /**
     * Creates new user with values from [User.RequestBody]
     * @param body [User.RequestBody] object
     */
    fun createUser(
        body: User.RequestBody,
    ): MutableLiveData<Response<User.Response>> {
        val userLiveData = MutableLiveData<Response<User.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.createUser(body)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "createUser: $response, ${response.errorBody()!!.string()}")
//                }
                userLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "createUser: Failed, $e")
            }
        }
        return userLiveData
    }

    /**
     * Gets a user from the DB
     * @param userName the user's user name
     * @param userRn the user's unique resource name
     */
    fun getUser(
        userName: String,
        userRn: String
    ): MutableLiveData<Response<User.Response>> {
        val userLiveData = MutableLiveData<Response<User.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.getUser(userName, userRn)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "getUser: $response, ${response.errorBody()!!.string()}")
//                }
                userLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "getUser: Failed, $e")
            }
        }
        return userLiveData
    }

    /**
     * Updates a user's information
     * @param userName the user's user name
     * @param body [User.RequestBody] object
     */
    fun updateUser(
        userName: String,
        body: User.RequestBody
    ): MutableLiveData<Response<User.Response>> {
        val userLiveData = MutableLiveData<Response<User.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.updateUser(userName, body)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "updateUser: $response, ${response.errorBody()!!.string()}")
//                }
                userLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "updateUser: Failed, $e")
            }
        }
        return userLiveData
    }

    /**
     * Deletes a user
     * @param userName the user's user name
     * @param body [User.RequestBodyAuth] object
     */
    fun deleteUser(
        userName: String,
        body: User.RequestBodyAuth
    ): MutableLiveData<Response<User.Response>> {
        val userLiveData = MutableLiveData<Response<User.Response>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.deleteUser(userName, body)
//                if (!response.isSuccessful) {
//                    // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "deleteUser: $response, ${response.errorBody()!!.string()}")
//                }
                userLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "deleteUser: Failed, $e")
            }
        }
        return userLiveData
    }

    // =================
    // AUTH

    /**
     * Checks if user is authorized
     * @param userName the user's user name
     * @param body [User.RequestBodyAuth] object
     */
    fun checkUserAuthorized(
        userName: String,
        body: User.RequestBodyAuth
    ): MutableLiveData<Response<User.ResponseAuth>> {
        val authLiveData = MutableLiveData<Response<User.ResponseAuth>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.checkUserAuthorized(userName, body)
//                if (!response.isSuccessful) {
//                     // Caller should handle error responses
//                    // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
////                    Log.e(Repository::class.java.simpleName, "checkUserAuthorized: $response, ${response.errorBody()!!.string()}")
//                }
                authLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "checkUserAuthorized: Failed, $e")
            }
        }
        return authLiveData
    }

    /**
     * Checks if user is authorized
     * @param userName the user's user name
     * @param body [User.RequestBodyAuth] object
     */
    fun updateUserPassword(
        userName: String,
        body: User.RequestBodyAuth
    ): MutableLiveData<Response<User.ResponseAuth>> {
        val authLiveData = MutableLiveData<Response<User.ResponseAuth>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.updateUserPassword(userName, body)
//                if (!response.isSuccessful) {
                // Caller should handle error responses
                // For debug purposes; errorBody()!!.string() is consumed and the string value is lost on the next call
//                    Log.e(Repository::class.java.simpleName, "updateUserPassword: $response, ${response.errorBody()!!.string()}")
//                }
                authLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "updateUserPassword: Failed, $e")
            }
        }
        return authLiveData
    }

    /***********************************************************************************************
     * MISC
     */

    /**
     * Pings the connection of AWS server
     */
    fun ping() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = aws.ping()
                Log.i(AWSRepo::class.java.simpleName, "ping: response $response, ${response.errorBody()!!.string()}")
            } catch (e: java.lang.Exception) {
                Log.e(AWSRepo::class.java.simpleName, "ping: Failed, $e")
            }
        }
    }
}