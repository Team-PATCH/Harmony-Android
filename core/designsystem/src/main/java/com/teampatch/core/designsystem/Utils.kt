package com.teampatch.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
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

@Composable
fun previewPlaceholder(@DrawableRes resourceId: Int): Painter? {
    if (LocalInspectionMode.current) {
        return painterResource(resourceId)
    }
    return null
}