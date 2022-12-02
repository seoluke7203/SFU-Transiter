package com.example.sfutransiter.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sfutransiter.backend.RetrofitInterface
import com.example.sfutransiter.model.Bus
import com.example.sfutransiter.model.BusStop
import com.example.sfutransiter.model.StopEstimate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

/**
 * class for communication between TransLink API and our model
 * @see <a href="https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources/rtti">Real-time Transit Information (RTTI)</a>
 */
class TLinkRepo(retrofit: Retrofit) : Repository() {
    private val tLink = retrofit.create(RetrofitInterface.TLink::class.java)

    /**
     * Get the buses by route
     * @param routeId the route (e.g. 145)
     */
    fun getBusesByRoute(routeId: String): MutableLiveData<Response<Array<Bus>>> {
        val busesLiveData = MutableLiveData<Response<Array<Bus>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = tLink.getBusesByRoute(routeId)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "getBuses: $response")
                }
                busesLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(Repository::class.java.simpleName, "getBuses: Failed to get data, $e")
            }
        }
        return busesLiveData
    }

    /**
     * Get the buses by stop number
     * @param stopId the stop (e.g. 60980)
     */
    fun getBusesByStop(stopId: String): MutableLiveData<Response<Array<Bus>>> {
        val busesLiveData = MutableLiveData<Response<Array<Bus>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = tLink.getBusesByStop(stopId)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "getBuses: $response")
                }
                busesLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(Repository::class.java.simpleName, "getBuses: Failed to get data, $e")
            }
        }
        return busesLiveData
    }

    /**
     * Get the stops near lat long coordinates
     * @param lat latitude
     * @param long longitude
     * @param radius the radius to search in meters (is defaulted to 500)
     * @param routeNo the route (e.g 144)
     * */
    fun getStopsNear(
        lat: Double,
        long: Double,
        radius: Int? = null,
        routeNo: String? = null
    ): MutableLiveData<Response<Array<BusStop>>> {
        val stopsLiveData = MutableLiveData<Response<Array<BusStop>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = tLink.getStopsNear(lat, long, radius, routeNo)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "getStopsNear: $response")
                }
                stopsLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(Repository::class.java.simpleName, "getStopsNear: Failed to get data, $e")
            }
        }
        return stopsLiveData
    }

    /**
     * Get estimated schedules for a stop
     * @param stopNo the stop identifier (5 digits)
     * @param count  the number of buses to return. Default 6
     * @param timeframe the search time frame in minutes. Default 120
     * @param routeNo the route (e.g 144)
     */
    fun getStopEstimates(
        stopNo: Int,
        count: Int? = null,
        timeframe: Int? = null,
        routeNo: String? = null
    ): MutableLiveData<Response<Array<StopEstimate>>> {
        val stopEstimatesLiveData = MutableLiveData<Response<Array<StopEstimate>>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = tLink.getStopEstimates(stopNo, count, timeframe, routeNo)
                if (!response.isSuccessful) {
                    // Caller should handle error responses
                    Log.e(Repository::class.java.simpleName, "getStopEstimates: $response")
                }
                stopEstimatesLiveData.postValue(response)
            } catch (e: java.lang.Exception) {
                Log.e(Repository::class.java.simpleName, "getStopEstimates: Failed to get data, $e")
            }
        }
        return stopEstimatesLiveData
    }
}