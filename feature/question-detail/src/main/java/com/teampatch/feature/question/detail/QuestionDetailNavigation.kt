package com.teampatch.feature.question.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDetailRoute(val questionId: String)

fun NavController.navigateToQuestionDetailScreen(
    questionId: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    navigate(
        route = QuestionDetailRoute(questionId),
        navOptions = navOptions,
        navigatorExtras = navigatorExtras
    )
}

fun NavGraphBuilder.addQuestionDetailScreen(
    onBackRequest: () -> Unit,
    answerEditPageRequest: () -> Unit,
) {
    composable<QuestionDetailRoute> {
        QuestionDetailRoute(
            onBackRequest = onBackRequest,
            answerEditPageRequest = answerEditPageRequest
        )
    }
}