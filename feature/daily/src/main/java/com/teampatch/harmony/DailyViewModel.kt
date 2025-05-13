package com.teampatch.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.usecase.daily.AddDailyRoutineUseCase
import com.teampatch.core.domain.usecase.daily.GetDailyRoutineUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.harmony.model.DailySideEffect
import com.teampatch.harmony.model.DailyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDailyRoutineUseCase: GetDailyRoutineUseCase,
    private val addDailyRoutineUseCase: AddDailyRoutineUseCase,
) : ViewModel() {

    var dailyUiState = mutableStateOf(DailyUiState())
        private set

    private val _sideEffect = Channel<DailySideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        try {
            val user = getUserInfoUseCase().first()
            val todo = getDailyRoutineUseCase().map { pagingData ->
                pagingData.map {
                    CheckableData(it, mutableStateOf(it.isFinished))
                }
            }
            dailyUiState.value = DailyUiState(user = user, daily = todo, isLoading = false)
        } catch (e: Exception) {
            _sideEffect.send(DailySideEffect.LoadError(e))
            e.printStackTrace()
        }
    }

    fun addDailyRoutine(id: String, title: String, time: LocalDateTime, isFinished: Boolean) {
        viewModelScope.launch {
            val input = Todo(title = title, dateTime = time, id = id, isFinished = isFinished)
            val result = addDailyRoutineUseCase(input)
            if (result.isFailure) {
                _sideEffect.send(DailySideEffect.LoadError(Exception("일과 추가 실패")))
            } else {
                load() // 추가 후 다시 목록 갱신
            }
        }
    }
}