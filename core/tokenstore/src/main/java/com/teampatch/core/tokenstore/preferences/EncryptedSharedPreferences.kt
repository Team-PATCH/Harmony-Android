package com.teampatch.core.tokenstore.preferences

import android.content.SharedPreferences
import com.teampatch.core.tokenstore.TokenLocalDataSource
import javax.inject.Inject

internal class EncryptedSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenLocalDataSource {

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN, null) ?: ""
    }

    override fun setAccessToken(token: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, token)
            .apply()
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, null) ?: ""
    }

    override fun setRefreshToken(token: String) {
        sharedPreferences.edit()
            .putString(REFRESH_TOKEN, token)
            .apply()
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}