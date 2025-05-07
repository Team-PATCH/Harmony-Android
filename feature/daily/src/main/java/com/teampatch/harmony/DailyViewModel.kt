package com.teampatch.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.domain.usecase.daily.GetDailyRoutineUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.harmony.model.DailySideEffect
import com.teampatch.harmony.model.DailyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    var dailyUiState = mutableStateOf(DailyUiState())
        private set

    private val _sideEffect = Channel<DailySideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
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
}