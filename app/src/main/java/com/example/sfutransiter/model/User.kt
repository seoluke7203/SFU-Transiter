package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("rn") val rn: String,
    @SerializedName("userName") val userName: String
)
