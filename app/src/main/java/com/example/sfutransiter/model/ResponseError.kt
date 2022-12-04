package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

data class ResponseError(@SerializedName("message") val message: String)
