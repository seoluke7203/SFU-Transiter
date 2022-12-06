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
            @Body body: BusStopReview.RequestBody
        ): Response<BusStopReview.Response>

        @Headers("Content-Type: application/json")
        @PUT("busStop/{busStop}/stopReview/{stopReviewRn}")
        suspend fun updateBusStopReview(
            @Path("busStop") stopNo: String,
            @Path("stopReviewRn") stopReviewRn: String,
            @Body body: BusStopReview.RequestBody
        ): Response<BusStopReview.Response>

        @Headers("Content-Type: application/json")
        @DELETE("busStop/{busStop}/stopReview/{stopReviewRn}")
        suspend fun deleteBusStopReview(
            @Path("busStop") stopNo: String,
            @Path("stopReviewRn") stopReviewRn: String,
        ): Response<BusStopReview.Response>

        @Headers("Content-Type: application/json")
        @GET("busStop/{busStop}/stop_reviews")
        suspend fun listBusStopReviews(
            @Path("busStop") stopNo: String
        ): Response<BusStopReview.ResponseList>

        // ==========================================================================================
        // USERS
        @Headers("Content-Type: application/json")
        @POST("user")
        suspend fun createUser(
            @Body body: User.RequestBody
        ): Response<User.Response>

        @Headers("Content-Type: application/json")
        @GET("user/userName/{userName}/userRn/{userRn}")
        suspend fun getUser(
            @Path("userName") userName: String,
            @Path("userRn") userRn: String
        ): Response<User.Response>

        @Headers("Content-Type: application/json")
        @PUT("user/userName/{userName}")
        suspend fun updateUser(
            @Path("userName") userName: String,
            @Body body: User.RequestBody
        ): Response<User.Response>

        @Headers("Content-Type: application/json")
        @HTTP(method = "DELETE", path = "user/userName/{userName}", hasBody = true)
        suspend fun deleteUser(
            @Path("userName") userName: String,
            @Body body: User.RequestBodyAuth
        ): Response<User.Response>

        // ==============
        // AUTH
        @Headers("Content-Type: application/json")
        @PUT("userAuth/userName/{userName}/auth")
        suspend fun checkUserAuthorized(
            @Path("userName") userName: String,
            @Body body: User.RequestBodyAuth
        ): Response<User.ResponseAuth>

        // TODO backend wrong path param joi schema
        @Headers("Content-Type: application/json")
        @PUT("userAuth/userName/{userName}")
        suspend fun updateUserPassword(
            @Path("userName") userName: String,
            @Body body: User.RequestBodyAuth
        ): Response<User.ResponseAuth>

        @GET("ping")
        suspend fun ping(): Response<Void>
    }
}