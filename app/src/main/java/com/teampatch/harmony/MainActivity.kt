package com.teampatch.harmony

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.v2.auth.BuildConfig
import com.teampatch.core.designsystem.theme.HarmonyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HarmonyTheme {
                MainNavHost()
            }
        }
        showHashKey()
    }

    private fun showHashKey() {
        if (BuildConfig.DEBUG) {
            val keyHash = Utility.getKeyHash(this)
            Log.d(TAG, keyHash)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}