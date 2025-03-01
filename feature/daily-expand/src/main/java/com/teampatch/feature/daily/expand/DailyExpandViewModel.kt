package com.teampatch.feature.daily.expand

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.daily.GetDailyUseCase
import com.teampatch.feature.daily.expand.model.DailyExpandSideEffect
import com.teampatch.feature.daily.expand.model.DailyExpandUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyExpandViewModel @Inject constructor(
    private val getDailyUseCase: GetDailyUseCase,
) : ViewModel() {
    var dailyExpandUiState = mutableStateOf(DailyExpandUiState())
        private set

    private val _sideEffect = Channel<DailyExpandSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        val daily = getDailyUseCase(-1).catch {
            _sideEffect.send(DailyExpandSideEffect.LoadError(it))
            it.printStackTrace()
        }
        dailyExpandUiState.value = DailyExpandUiState(
            daily = daily,
            isLoading = false
        )
    }
}