package com.teampatch.core.designsystem

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T: Any> Throwable.toPagingData(): Flow<PagingData<T>> {
    val errorLoadStates = LoadStates(
        refresh = LoadState.Error(this),
        prepend = LoadState.Error(this),
        append = LoadState.Error(this)
    )
    return flowOf(PagingData.empty(errorLoadStates))
}