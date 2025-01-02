package com.teampatch.core.data.entity

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teampatch.core.domain.entity.TokenManager
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TokenManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenManager() {

    override val isTokenInvalidListener: Flow<Unit> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == ACCESS_TOKEN_KEY && getAccessToken() == "") {
                trySendBlocking(Unit)
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override fun getAccessToken(): String = sharedPreferences.getString(ACCESS_TOKEN_KEY, null) ?: ""

    override fun setAccessToken(token: String) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, token)
        }
    }
}