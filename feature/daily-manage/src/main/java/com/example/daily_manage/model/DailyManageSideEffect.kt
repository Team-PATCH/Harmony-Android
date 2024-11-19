package com.example.daily_manage.model

internal sealed interface DailyManageSideEffect {

    data class LoadError(val t: Throwable) : DailyManageSideEffect

    data class AddCommentError(val t: Throwable) : DailyManageSideEffect
    data class EditCommentError(val t: Throwable) : DailyManageSideEffect
    data class DeleteCommentError(val t: Throwable) : DailyManageSideEffect
}