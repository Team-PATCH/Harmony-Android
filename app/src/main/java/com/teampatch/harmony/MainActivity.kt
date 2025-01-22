package com.teampatch.harmony

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.v2.auth.BuildConfig
import com.teampatch.core.designsystem.component.DefaultBottomNavigation
import com.teampatch.core.designsystem.component.NavigationItem
import com.teampatch.core.designsystem.theme.HarmonyTheme
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.home.navigateToHomeScreen
import com.teampatch.feature.onboarding.login.ui.navigateToOnboardingScreen
import com.teampatch.feature.question.QuestionRoute
import com.teampatch.feature.question.navigateToQuestionScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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