package com.teampatch.harmony

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.teampatch.core.designsystem.component.DefaultBottomNavigation
import com.teampatch.core.designsystem.component.NavigationItem
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.home.navigateToHomeScreen
import com.teampatch.feature.question.QuestionRoute
import com.teampatch.feature.question.navigateToQuestionScreen

@Composable
fun MainApp() {
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