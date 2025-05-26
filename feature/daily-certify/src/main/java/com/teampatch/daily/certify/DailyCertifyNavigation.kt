package com.teampatch.daily.certify

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.teampatch.core.domain.model.DailyComment
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
    onBackRequest: () -> Unit,
    onCommentEditRequest: (DailyComment?) -> Unit,
    onCertifyComplete: () -> Unit,
    onOpenCommentSheet: () -> Unit,
    onNavigateToDetail: () -> Unit,
) {
    composable<DailyCertifyScreenRoute> {
        DailyCertifyRoute(
            onBackRequest = onBackRequest,
            onCommentEditRequest = onCommentEditRequest,
            onCertifyComplete = onCertifyComplete,
            onOpenCommentSheet = onOpenCommentSheet,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Serializable
data object DailyCertifyDetailScreenRoute

fun NavGraphBuilder.addDailyCertifyDetailScreen(
    onBackRequest: () -> Unit,
    onCommentEditRequest: (DailyComment?) -> Unit,
    onOpenCommentSheet: () -> Unit,
) {
    composable<DailyCertifyDetailScreenRoute> {
        DailyCertifyDetailRoute(
            onBackRequest = onBackRequest,
            onCommentEditRequest = onCommentEditRequest,
            onOpenCommentSheet = onOpenCommentSheet
        )
    }
}