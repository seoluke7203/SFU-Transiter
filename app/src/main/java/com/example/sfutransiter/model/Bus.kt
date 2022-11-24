package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Bus(
    @SerializedName("VehicleNo")
    val vehicleNo: String,
    @SerializedName("TripId")
    val tripId: Long,
    @SerializedName("RouteNo")
    val routeNo: String,
    @SerializedName("Direction")
    val direction: String,
    @SerializedName("Destination")
    val destination: String,
    @SerializedName("Pattern")
    val pattern: String,
    @SerializedName("Latitude")
    val latitude: Double,
    @SerializedName("Longitude")
    val longitude: Double,
    @SerializedName("RecordedTime")
    val recordedTime: String,
    @SerializedName("RouteMap")
    val routeMap: RouteMap,
)
