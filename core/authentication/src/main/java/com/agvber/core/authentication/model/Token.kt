package com.agvber.core.authentication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Token(
    val userId: String,
    val nickname: String,
    val profileUrl: String,
    val authProvider: String,
    val accessToken: String,
    val accessTokenExpiresAt: Date,
    val refreshToken: String,
    val refreshTokenExpiresAt: Date,
) : Parcelable