package com.teampatch.core.network.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupOrLoginResponse(
    @Json(name = "message") val message: String,
    @Json(name = "user") val user: UserResponse,
    @Json(name = "token") val token: String
)