package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

class User {
    /**
     * @param userName Required only for creating new user
     */
    data class RequestBody(
        @SerializedName("userName")
        val userName: String? = null,
        @SerializedName("password")
        val password: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
    )

    /**
     * @param newPassword Required for updating password
     */
    data class RequestBodyAuth(
        @SerializedName("password")
        val password: String,
        @SerializedName("newPassword")
        val newPassword: String? = null,
    )

    data class Response(
        @SerializedName("userRn")
        val userRn: String,
        @SerializedName("userName")
        val userName: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("status")
        val status: String,
    )

    data class ResponseAuth(
        @SerializedName("userRn")
        val userRn: String,
        @SerializedName("userName")
        val userName: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("authorized")
        val authorized: Boolean,
    )
}
