package com.teampatch.harmony

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teampatch.feature.answer.addAnswerScreen
import com.teampatch.feature.answer.navigateToAnswerScreen
import com.teampatch.feature.family.info.addFamilyInfoScreen
import com.teampatch.feature.family.info.navigateToFamilyInfoScreen
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.home.addHomeScreen
import com.teampatch.feature.memorycard.registration.addMemoryCardRegistrationScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingEnterScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingInvitationScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingMakeGroupScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingPermissionNotificationScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingStartScreen
import com.teampatch.feature.onboarding.login.ui.navigateToEnterScreen
import com.teampatch.feature.onboarding.login.ui.navigateToInvitationScreen
import com.teampatch.feature.onboarding.login.ui.navigateToMakeGroupScreen
import com.teampatch.feature.onboarding.login.ui.navigateToPermissionNotificationScreen
import com.teampatch.feature.onboarding.login.ui.navigateToStartScreen
import com.teampatch.feature.profile.edit.addProfileEditScreen
import com.teampatch.feature.profile.edit.navigateToProfileEditScreen
import com.teampatch.feature.question.addQuestionScreen
import com.teampatch.feature.question.detail.QuestionDetailParams
import com.teampatch.feature.question.detail.addQuestionDetailScreen
import com.teampatch.feature.question.detail.navigateToQuestionDetailScreen
import com.teampatch.feature.question.expand.addQuestionExpandScreen
import com.teampatch.feature.question.expand.navigateToQuestionExpandScreen
import com.teampatch.feature.settings.SettingsRoute

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        addOnboardingScreen(
            onKakaoLoginRequest = {},
            onPermissionNotificationRequest = { navController.navigateToPermissionNotificationScreen() },
            onStartScreenRequest = { navController.navigateToStartScreen() }
        )

        addOnboardingPermissionNotificationScreen()
        addOnboardingStartScreen(
            onboardingMakeGroupRequest = { navController.navigateToMakeGroupScreen() },
            onboardingEnterScreenRequest = { navController.navigateToEnterScreen() }
        )
        addOnboardingMakeGroupScreen()
        addOnboardingEnterScreen(
            onNextClick = { navController.navigateToInvitationScreen() }
        )

        addOnboardingInvitationScreen()

        addHomeScreen(
            onUserPageRequest = navController::navigateToFamilyInfoScreen,
            onDailyRoutineClick = { },
            onDailyRoutineRegisterPageRequest = { },
            onMemoryCardClick = { }
        )

        addQuestionScreen(
            questionDetailPageRequest = navController::navigateToQuestionDetailScreen,
            answerPageRequest = navController::navigateToAnswerScreen,
            questionExpandPageRequest = navController::navigateToQuestionExpandScreen
        )

        addQuestionExpandScreen(
            onBackRequest = navController::popBackStack,
            questionDetailPageRequest = navController::navigateToQuestionDetailScreen
        )

        addQuestionDetailScreen(
            onBackRequest = navController::popBackStack,
            answerEditPageRequest = navController::navigateToAnswerScreen
        )

        addAnswerScreen(
            onBackRequest = navController::popBackStack,
            onCompleteRequest = { answer ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    key = QuestionDetailParams.ANSWER_UPDATE_DATA,
                    value = answer
                )
                navController.popBackStack()
            }
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
            onProfileEditClick = navController::navigateToProfileEditScreen
        )

        addMemoryCardRegistrationScreen(
            onDismissRequest = navController::popBackStack,
            onMemoryStorePageRequest = { } // TODO: 메모리 저장소 페이지 가기
        )

        addProfileEditScreen(
            onCompleteRequest = navController::popBackStack
        )
    }
}