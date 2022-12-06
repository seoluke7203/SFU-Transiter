package com.example.sfutransiter.model

import com.google.gson.annotations.SerializedName

class User {
    data class CurrentUser(
        val userRn: String,
        val userName: String,
        val email: String,
        val firstName: String,
        val lastName: String,
    )

    companion object {
        private var instance: CurrentUser? = null

        @JvmStatic
        fun setCurrentUser(
            userRn: String,
            userName: String,
            email: String = "",
            firstName: String = "",
            lastName: String = ""
        ) {
            instance = CurrentUser(userRn, userName, email, firstName, lastName)
        }

        @JvmStatic
        fun getCurrentUser() = instance ?: CurrentUser("", "", "", "", "")
    }

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
