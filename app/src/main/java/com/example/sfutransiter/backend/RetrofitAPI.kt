package com.example.sfutransiter.backend

import com.example.sfutransiter.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_TRANSLINK_API_URL = "https://api.translink.ca/rttiapi/v1/"

object RetrofitAPI {
    fun getTransLinkInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_TRANSLINK_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAWSInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AWS_API_KEY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}