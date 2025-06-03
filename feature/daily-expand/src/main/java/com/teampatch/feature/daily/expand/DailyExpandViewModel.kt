package com.teampatch.feature.daily.expand

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.common.toPagingData
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.usecase.daily.GetDailyRoutineUseCase
import com.teampatch.feature.daily.expand.model.DailyExpandEvent
import com.teampatch.feature.daily.expand.model.DailyExpandUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class DailyExpandViewModel @Inject constructor(
    private val getDailyRoutineUseCase: GetDailyRoutineUseCase,
) : ViewModel() {

    private val _dailyExpandUiState = MutableStateFlow(DailyExpandUiState())
    val dailyExpandUiState: StateFlow<DailyExpandUiState> = _dailyExpandUiState.asStateFlow()

    private val _event = Channel<DailyExpandEvent>()
    val event = _event.receiveAsFlow()

    val dailyRoutine: Flow<PagingData<Todo>> =
        flowErrorCatch(
            block = {
                getDailyRoutineUseCase()
                    .cachedIn(viewModelScope)
            }
        ) {
            it.printStackTrace()
            emit(it.toPagingData())
        }

    init {
        _dailyExpandUiState.update {
            it.copy(
                isLoading = false
            )
        }
    }
}