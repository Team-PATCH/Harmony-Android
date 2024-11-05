package com.teampatch.feature.onboarding.login.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable

const val ONBOARDING_ROUTE = "Onboarding"

fun NavController.navigateToOnboardingScreen(
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    navigate(route = ONBOARDING_ROUTE, navOptions = navOptions, navigatorExtras = navigatorExtras)
}

fun NavGraphBuilder.addOnboardingScreen(
    title: String,
    content: @Composable () -> Unit,  // content 인자를 받음
    onBackClick: () -> Unit,
    onNextClick: (() -> Unit)? = null
) {
    composable(ONBOARDING_ROUTE) {
//        OnboardingScreen(
//            title = title,
//            onBackClick = onBackClick,
//            onNextClick = onNextClick,
//            content = content
//        )
    }
}