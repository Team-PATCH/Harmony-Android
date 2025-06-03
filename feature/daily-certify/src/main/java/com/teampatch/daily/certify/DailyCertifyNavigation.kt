package com.teampatch.daily.certify

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object DailyCertifyAlarmScreenRoute

fun NavController.navigateToDailyCertifyAlarmScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyCertifyAlarmScreenRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyCertifyAlarmScreen(
    onPickImageScreenRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    fromNotification: Boolean,
) {
    composable<DailyCertifyAlarmScreenRoute> {
        DailyAlarmRoute(
            onPickImageScreenRequest = onPickImageScreenRequest,
            onDismissRequest = onDismissRequest,
            fromNotification = fromNotification
        )
    }
}

@Serializable
data object DailyCertifyScreenRoute

fun NavController.navigateToDailyCertifyScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyCertifyScreenRoute, navOptions, navigatorExtras)
}

fun NavGraphBuilder.addDailyCertifyScreen(
    viewModel: DailyCertifyViewModel,
    onBackRequest: () -> Unit,
    onCertifyCompleteRequest: () -> Unit,
    onNavigateToDetailRequest: () -> Unit,
) {
    composable<DailyCertifyScreenRoute> {
        DailyCertifyRoute(
            viewModel = viewModel,
            onBackRequest = onBackRequest,
            onCertifyCompleteRequest = onCertifyCompleteRequest,
            onNavigateToDetailRequest = onNavigateToDetailRequest
        )
    }
}

@Serializable
data object DailyCertifyDetailScreenRoute

fun NavController.navigateToDailyCertifyDetailScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(DailyCertifyDetailScreenRoute, navOptions, navigatorExtras)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.addDailyCertifyDetailScreen(
    viewModel: DailyCertifyViewModel,
    onBackRequest: () -> Unit,
) {
    composable<DailyCertifyDetailScreenRoute> {
        DailyCertifyDetailRoute(
            viewModel = viewModel,
            onBackRequest = onBackRequest
        )
    }
}