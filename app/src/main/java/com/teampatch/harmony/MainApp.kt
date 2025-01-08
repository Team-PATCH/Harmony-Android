package com.teampatch.harmony

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

private val BottomNavigationEnableScreens: Set<String?> = hashSetOf(
    HomeRoute::class.qualifiedName,
    QuestionRoute::class.qualifiedName
)

@Composable
fun MainApp(viewModel: MainViewModel = hiltViewModel()) {
    val navController: NavHostController = rememberNavController()
    val currentBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        initialValue = null
    )
    var previousNavigationItem by rememberSaveable { mutableStateOf(NavigationItem.HOME) }
    val navigationItem: NavigationItem by remember(currentBackStackEntry) {
        mutableStateOf(
            when (currentBackStackEntry?.destination?.route) {
                HomeRoute::class.qualifiedName -> {
                    NavigationItem.HOME.also {
                        previousNavigationItem = it
                    }
                }

                QuestionRoute::class.qualifiedName -> {
                    NavigationItem.QUESTION.also {
                        previousNavigationItem = it
                    }
                }

                else -> previousNavigationItem
            }
        )
    }
    val isBottomNavigationShow: Boolean by remember(currentBackStackEntry) {
        derivedStateOf {
            currentBackStackEntry?.destination?.route in BottomNavigationEnableScreens
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavigationShow,
                enter = fadeIn(tween(400)),
                exit = fadeOut(tween(400))
            ) {
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
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .safeDrawingPadding()
    ) { scaffoldPaddingValue ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(scaffoldPaddingValue)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.isLoginRequiredFlow
            .distinctUntilChanged()
            .collectLatest { isRequired ->
                if (isRequired) {
                    navController.popBackStack(HomeRoute::class.qualifiedName.toString(), true)
//                    navController.navigateToOnboardingScreen()
                    viewModel.finishAppInit()
                }
            }
    }
}