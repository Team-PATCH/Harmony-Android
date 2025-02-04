package com.teampatch.harmony

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.v2.auth.BuildConfig
import com.teampatch.core.designsystem.theme.HarmonyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setKeepOnSplashScreenCondition()
        initView()
        showHashKey()
    }

    private fun setKeepOnSplashScreenCondition() {
        val content: View = findViewById(android.R.id.content)
        val preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                runBlocking { viewModel.isAppInitFinished.first { true } }
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        }
        content.viewTreeObserver.addOnPreDrawListener(preDrawListener)
    }

    private fun initView() = setContent {
        HarmonyTheme {
            MainApp(viewModel = viewModel)
        }
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