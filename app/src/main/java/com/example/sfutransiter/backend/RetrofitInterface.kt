package com.example.sfutransiter.backend

import com.example.sfutransiter.BuildConfig
import com.example.sfutransiter.model.*
import retrofit2.Response
import retrofit2.http.*

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
        @GET("buses?apikey=${BuildConfig.TRANSLINK_API_KEY}")
        suspend fun getBusesByStop(@Query("stopNo") stopNo: String): Response<Array<Bus>>

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
        @Headers("Content-Type: application/json")
        @POST("busStop/{busStop}")
        suspend fun insertBusStopReview(
            @Path("busStop") stopNo: String,
            @Body body: BusStopReview.Request
        ): Response<BusStopReview.Response>

        @Headers("Content-Type: application/json")
        @PUT("busStop/{busStop}/stopReview/{stopReviewRn}")
        suspend fun updateBusStopReview(
            @Path("busStop") stopNo: String,
            @Path("stopReviewRn") stopReviewRn: String,
            @Body body: BusStopReview.Request
        ): Response<BusStopReview.Response>

        @Headers("Content-Type: application/json")
        @GET("busStop/{busStop}/stop_reviews")
        suspend fun listBusStopReviews(
            @Path("busStop") stopNo: String
        ): Response<BusStopReview.ResponseList>

        @GET("ping")
        suspend fun ping(): Response<Void>
    }
}