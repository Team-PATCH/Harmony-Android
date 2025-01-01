package com.teampatch.harmony

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teampatch.feature.family.info.addFamilyInfoScreen
import com.teampatch.feature.family.info.navigateToFamilyInfoScreen
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.home.addHomeScreen
import com.teampatch.feature.memorycard.registration.addMemoryCardRegistrationScreen
import com.teampatch.feature.settings.SettingsRoute

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        addHomeScreen(
            onUserPageRequest = navController::navigateToFamilyInfoScreen,
            onDailyRoutineClick = { },
            onDailyRoutineRegisterPageRequest = { },
            onMemoryCardClick = { }
        )

        composable<SettingsRoute> {
            SettingsRoute(
                onBackRequest = navController::popBackStack,
                onExitAppRequest = { },
                onPrivacyPolicyClick = { },
                onTosClick = { }
            )
        }

        addFamilyInfoScreen(
            onBackRequest = navController::popBackStack,
            onSettingsClick = {},
            onProfileEditClick = {}
        )

        addMemoryCardRegistrationScreen(
            onDismissRequest = navController::popBackStack,
            onMemoryStorePageRequest = { } // TODO: 메모리 저장소 페이지 가기
        )
    }
}