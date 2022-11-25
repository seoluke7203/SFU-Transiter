package com.example.sfutransiter.backend

import com.example.sfutransiter.BuildConfig
import com.example.sfutransiter.model.Bus
import com.example.sfutransiter.model.BusStop
import com.example.sfutransiter.model.StopEstimate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    interface TLink {
        @Headers(
            "Accept: application/json",
            "Content-type: application/JSON"
        )
        @GET("buses?apikey=${BuildConfig.TRANSLINK_API_KEY}")
        suspend fun getBusesByRoute(@Query("routeNo") routeNo: String): Response<Array<Bus>>

        @Headers(
            "Accept: application/json",
            "Content-type: application/JSON"
        )
        @GET("stops?apikey=${BuildConfig.TRANSLINK_API_KEY}")
        suspend fun getStopsNear(
            @Query("lat") lat: Double,
            @Query("long") long: Double,
            @Query("radius") radius: Int?,
            @Query("routeNo") routeNo: String?
        ): Response<Array<BusStop>>

        @Headers(
            "Accept: application/json",
            "Content-type: application/JSON"
        )
        @GET("stops/{StopNo}/estimates?apikey=${BuildConfig.TRANSLINK_API_KEY}")
        suspend fun getStopEstimates(
            @Path("StopNo") stopNo: Int,
            @Query("count") count: Int?,
            @Query("timeframe") timeframe: Int?,
            @Query("routeNo") routeNo: String?
        ): Response<Array<StopEstimate>>
    }

    interface AWS {
        @GET("ping")
        suspend fun ping(): Response<Void>
    }
}