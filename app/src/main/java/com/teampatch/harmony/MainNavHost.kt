package com.teampatch.harmony

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teampatch.feature.family.info.FamilyInfoRoute
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.profile.edit.ProfileEditRoute
import com.teampatch.feature.settings.SettingsRoute

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeRoute(
                onUserPageRequest = { },
                onDailyRoutineClick = { },
                onDailyRoutineRegisterPageRequest = { },
                onMemoryCardClick = { }
            )
        }

        composable<SettingsRoute> {
            SettingsRoute(
                onBackRequest = navController::popBackStack,
                onExitAppRequest = { },
                onPrivacyPolicyClick = { },
                onTosClick = { }
            )
        }

        composable<ProfileEditRoute> {
            ProfileEditRoute(
                onCompleteRequest = { }
            )
        }

        composable<FamilyInfoRoute> {
            ProfileEditRoute(
                onCompleteRequest = { }
            )
        }
    }
}
