package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

data class StopEstimate(
    @SerializedName("RouteNo")
    val routeNo: String,
    @SerializedName("RouteName")
    val routeName: String,
    @SerializedName("Direction")
    val direction: String,
    @SerializedName("RouteMap")
    val routeMap: RouteMap,
    @SerializedName("Schedules")
    val schedules: ArrayList<Schedule>
)