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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import com.teampatch.feature.question.QuestionRoute
import com.teampatch.feature.question.navigateToQuestionScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setKeepOnSplashScreenCondition()
        initView()
        observeIsLoginRequiredEvent()
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
            val navController: NavHostController = rememberNavController()
            val currentBackStackEntry: NavBackStackEntry? by
                navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
            val navigationItem: NavigationItem by remember(currentBackStackEntry) {
                derivedStateOf {
                    when (currentBackStackEntry?.destination?.route) {
                        HomeRoute::class.qualifiedName -> {
                            NavigationItem.HOME
                        }

                        QuestionRoute::class.qualifiedName -> {
                            NavigationItem.QUESTION
                        }

                        else -> NavigationItem.HOME
                    }
                }
            }

            Scaffold(
                bottomBar = {
                    DefaultBottomNavigation(
                        onClick = {
                            when (it) {
                                NavigationItem.HOME -> navController.navigateToHomeScreen()
                                NavigationItem.STORE -> {}
                                NavigationItem.QUESTION -> navController.navigateToQuestionScreen()
                                NavigationItem.CALENDAR -> {}
                            }
                        },
                        navigationItem = navigationItem
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .safeDrawingPadding()
            ) { scaffoldPaddingValue ->
                MainNavHost(
                    navController = navController,
                    modifier = Modifier
                        .padding(scaffoldPaddingValue)
                )
            }
        }
    }

    private fun observeIsLoginRequiredEvent() = lifecycleScope.launch {
        try {
            viewModel.isLoginRequiredFlow.collectLatest { isRequired ->
                if (isRequired) {
                    // Onboarding 화면 가기
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
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