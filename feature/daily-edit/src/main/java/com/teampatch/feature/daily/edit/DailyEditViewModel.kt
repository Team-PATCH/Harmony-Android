package com.teampatch.feature.daily.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.daily.GetDailyManageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * DailyEdit를 위한 뷰모델과 Navigation이 덜 완성되었다... usecase도 더 만들어야될거같은데 민준님 도와주세요..
 */
@HiltViewModel
internal class DailyEditViewModel @Inject constructor(
    private val getDailyManageUseCase: GetDailyManageUseCase,
) : ViewModel() {
    var dailyEditUiState = mutableStateOf(DailyEditUiState())
        private set

    private val _event: Channel<DailyEditEvent> = Channel()
    val event: Flow<DailyEditEvent> = _event.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        runCatching {
            getDailyManageUseCase("someId") // 올바른 dailyId 사용
        }.onSuccess { dailyManage ->
            dailyEditUiState.value = DailyEditUiState(
                dailyExpand = dailyManage,
                isLoading = false,
                selectedDays = setOf(dailyManage.dateTime.dayOfWeek.name)
            )
        }.onFailure {
            _event.send(DailyEditEvent.LoadError(it))
            it.printStackTrace()
        }
    }

    fun toggleDaySelection(day: String) {
        dailyEditUiState.value = dailyEditUiState.value.copy(
            selectedDays = if (dailyEditUiState.value.selectedDays.contains(day)) {
                dailyEditUiState.value.selectedDays - day
            } else {
                dailyEditUiState.value.selectedDays + day
            }
        )
    }

    fun saveDaily(daily: String) = viewModelScope.launch {
        // UseCase를 호출하여 선택한 요일과 함께 저장
    }
}