package com.teampatch.feature.daily.expand

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.daily.GetDailyManageUseCase
import com.teampatch.feature.daily.expand.model.DailyExpandEvent
import com.teampatch.feature.daily.expand.model.DailyExpandUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyExpandViewModel @Inject constructor(
    private val getDailyManageUseCase: GetDailyManageUseCase,
) : ViewModel() {
    var dailyExpandUiState = mutableStateOf(DailyExpandUiState())
        private set

    private val _event = Channel<DailyExpandEvent>()
    val event = _event.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        runCatching {
            getDailyManageUseCase("someId") // 단일 데이터 반환
        }.onSuccess { daily ->
            dailyExpandUiState.value = DailyExpandUiState(
                dailyManage = daily,
                isLoading = false
            )
        }.onFailure {
            _event.send(DailyExpandEvent.LoadError(it))
            it.printStackTrace()
        }
    }
}