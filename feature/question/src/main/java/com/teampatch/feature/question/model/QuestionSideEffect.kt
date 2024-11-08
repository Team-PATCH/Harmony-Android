package com.teampatch.feature.question.model

internal sealed interface QuestionSideEffect {

    data class LoadError(val t: Throwable) : QuestionSideEffect
}