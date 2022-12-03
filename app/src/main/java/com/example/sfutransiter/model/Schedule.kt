package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("Pattern")
    val pattern: String,
    @SerializedName("Destination")
    val destination: String,
    @SerializedName("ExpectedLeaveTime")
    val expectedLeaveTime: String,
    @SerializedName("ExpectedCountdown")
    val expectedCountdown: Int,
    @SerializedName("ScheduleStatus")
    val scheduleStatus: String,
    @SerializedName("CancelledTrip")
    val cancelledTrip: Boolean,
    @SerializedName("CancelledStop")
    val cancelledStop: Boolean,
    @SerializedName("AddedTrip")
    val addedTrip: Boolean,
    @SerializedName("AddedStop")
    val addedStop: Boolean,
    @SerializedName("LastUpdate")
    val lastUpdate: String
)
