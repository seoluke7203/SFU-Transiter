package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

data class BusStop(
    @SerializedName("StopNo")
    val stopNo: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("BayNo")
    val bayNo: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("OnStreet")
    val onStreet: String,
    @SerializedName("AtStreet")
    val atStreet: String,
    @SerializedName("Latitude")
    val latitude: Double,
    @SerializedName("Longitude")
    val longitude: Double,
    @SerializedName("WheelchairAccess")
    val wheelchairAccess: Int,
    @SerializedName("Distance")
    val distance: Int,
    @SerializedName("Routes")
    val routes: String
)
