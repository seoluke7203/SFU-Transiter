package com.example.sfutransiter.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: Error
) {

    data class Error(@SerializedName("details") val details: ArrayList<Detail>) {
        data class Detail(@SerializedName("message") val message: String)
    }

    companion object {
        @JvmStatic
        fun fromJsonString(json: String): ResponseError {
            return Gson().fromJson(json, ResponseError::class.java)
        }
    }
}
