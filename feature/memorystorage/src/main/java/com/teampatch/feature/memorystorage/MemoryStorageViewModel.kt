package com.teampatch.feature.memorystorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.teampatch.core.domain.model.MemoryCard
import com.teampatch.core.domain.usecase.memory.GetMemoryCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@HiltViewModel
internal class MemoryStorageViewModel @Inject constructor(
    private val getMemoryCardsUseCase: GetMemoryCardsUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MemoryStorageUiState>(MemoryStorageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortOption = MutableStateFlow("오래된순")
    val sortOption: StateFlow<String> = _sortOption.asStateFlow()

    val memoryCards: Flow<PagingData<MemoryCard>> =
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getMemoryCardsUseCase()
                    .map { pagingData ->
                        pagingData.filter {
                            it.text.contains(query, ignoreCase = true)
                        }
                    }
            }
            .cachedIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}