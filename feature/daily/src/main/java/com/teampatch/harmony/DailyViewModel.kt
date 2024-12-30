package com.teampatch.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.daily.GetDailyUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.harmony.model.DailySideEffect
import com.teampatch.harmony.model.DailyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDailyUseCase: GetDailyUseCase,
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
            val daily = getDailyUseCase()
            dailyUiState.value = DailyUiState(user = user, daily = daily, isLoading = false)
        } catch (e: Exception) {
            _sideEffect.send(DailySideEffect.LoadError(e))
            e.printStackTrace()
        }
    }
}