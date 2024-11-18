package com.example.daily_manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_manage.model.DailyManageSideEffect
import com.example.daily_manage.model.DailyManageUiState
import com.teampatch.core.domain.usecase.daily.GetDailyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DailyManageViewModel @Inject constructor(
    private val getDailyUseCase: GetDailyUseCase
) : ViewModel() {

    var dailyManageUiState = mutableStateOf(DailyManageUiState())
        private set

    private val _sideEffect = Channel<DailyManageSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            val daily = getDailyUseCase()
            dailyManageUiState.value = DailyManageUiState(daily = daily)
        } catch (e: Exception) {
            _sideEffect.send(DailyManageSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }
}