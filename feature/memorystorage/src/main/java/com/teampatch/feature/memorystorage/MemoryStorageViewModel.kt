package com.teampatch.feature.memorystorage

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.usecase.memory.GetMemoryCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
internal class MemoryStorageViewModel @Inject constructor(
    private val getMemoryCardsUseCase: GetMemoryCardsUseCase,
) : ViewModel() {

    private val _memoryStorageUiState =
        MutableStateFlow<MemoryStorageUiState>(MemoryStorageUiState.Loading)
    val memoryStorageUiState = _memoryStorageUiState.asStateFlow()

    val memoryCards: Flow<PagingData<MemoryCard>> = flowErrorCatch(
        block = { getMemoryCardsUseCase() },
        action = { it.printStackTrace() }
    )
}