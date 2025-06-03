package com.teampatch.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.teampatch.core.common.flowErrorCatch
import com.teampatch.core.common.toPagingData
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.usecase.daily.GetDailyRoutineUseCase
import com.teampatch.core.domain.usecase.daily.ToggleDailyRoutineStatusUseCase
import com.teampatch.harmony.model.DailyErrorHandler
import com.teampatch.harmony.model.DailySideEffect
import com.teampatch.harmony.model.DailyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyViewModel @Inject constructor(
    private val getDailyRoutineUseCase: GetDailyRoutineUseCase,
    private val toggleDailyRoutineStatusUseCase: ToggleDailyRoutineStatusUseCase,
) : ViewModel() {

    private val _dailyUiState = MutableStateFlow(DailyUiState())
    val dailyUiState: StateFlow<DailyUiState> = _dailyUiState.asStateFlow()

    private val _sideEffect = Channel<DailySideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _errorHandler = MutableSharedFlow<DailyErrorHandler>()
    val errorHandler: SharedFlow<DailyErrorHandler> = _errorHandler.asSharedFlow()

    val dailyRoutine: Flow<PagingData<CheckableData<Todo>>> =
        flowErrorCatch(
            block = {
                getDailyRoutineUseCase()
                    .map { pagingData ->
                        pagingData.map {
                            CheckableData(it, mutableStateOf(it.isFinished))
                        }
                    }
                    .cachedIn(viewModelScope)
            }
        ) {
            it.printStackTrace()
            emit(it.toPagingData())
        }

    init {
        _dailyUiState.update {
            it.copy(
                dailyRoutine = dailyRoutine,
                isLoading = false // 또는 refresh 상태를 기반으로 갱신
            )
        }
    }

    fun changeDailyRoutine(todo: CheckableData<Todo>, checked: Boolean) = viewModelScope.launch {
        try {
            // 1. UI 상태 변경
            todo.checked.value = checked

            // 2. 서버 상태 반영
            toggleDailyRoutineStatusUseCase(todo.data.id, checked)
        } catch (e: Exception) {
            e.printStackTrace()
            _errorHandler.emit(DailyErrorHandler.ChangeRoutineError(e))
        }
    }
}