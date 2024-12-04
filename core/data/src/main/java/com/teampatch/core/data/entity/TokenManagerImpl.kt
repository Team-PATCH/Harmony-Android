package com.teampatch.core.data.entity

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teampatch.core.domain.entity.TokenManager
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenManager() {

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null) ?: ""
    }

    override fun setAccessToken(token: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, token)
        }
    }
}