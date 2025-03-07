package com.teampatch.harmony

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.teampatch.core.common.findActivity
import com.teampatch.feature.answer.addAnswerScreen
import com.teampatch.feature.answer.navigateToAnswerScreen
import com.teampatch.feature.daily.edit.navigateToDailyEditScreen
import com.teampatch.feature.daily.expand.addDailyExpandScreen
import com.teampatch.feature.family.info.addFamilyInfoScreen
import com.teampatch.feature.family.info.navigateToFamilyInfoScreen
import com.teampatch.feature.home.HomeRoute
import com.teampatch.feature.home.addHomeScreen
import com.teampatch.feature.home.navigateToHomeScreen
import com.teampatch.feature.memorycard.registration.addMemoryCardRegistrationScreen
import com.teampatch.feature.memorycard.registration.navigateToMemoryCardRegistrationScreen
import com.teampatch.feature.onboarding.login.ui.OnboardingRoute
import com.teampatch.feature.onboarding.login.ui.addOnboardingEnterInvitationCodeScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingEnterSpaceScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingMakeGroupScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingPermissionNotificationScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingScreen
import com.teampatch.feature.onboarding.login.ui.addOnboardingStartScreen
import com.teampatch.feature.onboarding.login.ui.navigateToEnterInvitationCodeScreen
import com.teampatch.feature.onboarding.login.ui.navigateToEnterSpaceScreen
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
import com.teampatch.feature.settings.addSettingsScreen
import com.teampatch.feature.settings.navigateToSettingsScreen

@Composable
fun MainNavHost(
    isLoginRequired: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoginRequired) OnboardingRoute else HomeRoute
    ) {
        addOnboardingScreen(
            onKakaoLoginRequest = {},
            onPermissionNotificationRequest = { navController.navigateToPermissionNotificationScreen() },
            onStartScreenRequest = { navController.navigateToStartScreen() }
        )

        addOnboardingPermissionNotificationScreen()
        addOnboardingStartScreen(
            onBackRequest = navController::popBackStack,
            onboardingMakeGroupRequest = { navController.navigateToMakeGroupScreen() },
            onboardingEnterScreenRequest = { navController.navigateToEnterInvitationCodeScreen() }
        )
        addOnboardingMakeGroupScreen()
        addOnboardingEnterInvitationCodeScreen(
            onNextClick = { navController.navigateToEnterSpaceScreen() }
        )

        addOnboardingEnterSpaceScreen(onNextClick = { navController.navigateToHomeScreen() })

        addHomeScreen(
            onUserPageRequest = navController::navigateToFamilyInfoScreen,
            onDailyRoutineClick = { },
            onDailyRoutineRegisterPageRequest = { },
            onMemoryCardClick = navController::navigateToMemoryCardRegistrationScreen
        )

        addQuestionScreen(
            questionDetailPageRequest = navController::navigateToQuestionDetailScreen,
            answerPageRequest = navController::navigateToAnswerScreen,
            questionExpandPageRequest = navController::navigateToQuestionExpandScreen
        )

        addQuestionExpandScreen(
            onBackRequest = navController::navigateUp,
            questionDetailPageRequest = navController::navigateToQuestionDetailScreen
        )

        addQuestionDetailScreen(
            onBackRequest = navController::navigateUp,
            answerEditPageRequest = navController::navigateToAnswerScreen
        )

        addAnswerScreen(
            onBackRequest = navController::navigateUp,
            onCompleteRequest = { answer ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    key = QuestionDetailParams.ANSWER_UPDATE_DATA,
                    value = answer
                )
                navController.popBackStack()
            }
        )

        addSettingsScreen(
            onBackRequest = navController::navigateUp,
            onExitAppRequest = { context.findActivity()?.finishAffinity() },
            onPrivacyPolicyClick = { },
            onTosClick = { }
        )

        addFamilyInfoScreen(
            onBackRequest = navController::navigateUp,
            onSettingsClick = navController::navigateToSettingsScreen,
            onProfileEditClick = navController::navigateToProfileEditScreen
        )

        addMemoryCardRegistrationScreen(
            onDismissRequest = navController::navigateUp,
            onMemoryStorePageRequest = { } // TODO: 메모리 저장소 페이지 가기
        )

        addProfileEditScreen(
            onCompleteRequest = navController::navigateUp
        )

        addDailyScreen(
            dailyExpandPageRequest = { navController.navigateToDailyScreen() }
        )

        addDailyExpandScreen(
            onBackRequest = navController::navigateUp,
            dailyEditPageRequest = { navController.navigateToDailyEditScreen() },
            onDeleteClick = {} // 임시
        )
    }
}