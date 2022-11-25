package com.example.sfutransiter.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_TRANSLINK_API_URL = "https://api.translink.ca/rttiapi/v1/"
private const val BASE_AWS_API_URL = "https://xvgz7q0bol.execute-api.us-west-2.amazonaws.com/V1/"

object RetrofitAPI {
    fun getTransLinkInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_TRANSLINK_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAWSInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_AWS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}