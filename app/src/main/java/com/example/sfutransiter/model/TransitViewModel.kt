package com.example.sfutransiter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sfutransiter.repository.Repository
import retrofit2.Response

class TransitViewModel(private val repository: Repository) : ViewModel() {
    private var busesByRoute = MutableLiveData<Response<Array<Bus>>>()
    private var stopsNear = MutableLiveData<Response<Array<BusStop>>>()

    fun getBusesByRoute(routeId: String): LiveData<Response<Array<Bus>>> {
        busesByRoute = repository.getBusesByRoute(routeId)
        return busesByRoute
    }

    fun getStopsNear(
        lat: Double,
        long: Double,
        radius: Int? = null,
        routeId: String? = null
    ): LiveData<Response<Array<BusStop>>> {
        stopsNear = repository.getStopsNear(lat, long, radius, routeId)
        return stopsNear
    }
}