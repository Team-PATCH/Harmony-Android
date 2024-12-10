package com.teampatch.feature.memory.storage

import androidx.paging.PagingData
import com.teampatch.core.domain.model.MemoryStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MemoryStorageDetailUiState(
    val memoryStorage: Flow<PagingData<MemoryStorage>> = emptyFlow(),
    val isLoading: Boolean = true
)