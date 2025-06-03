package com.teampatch.feature.daily.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.daily.GetDailyManageUseCase
import com.teampatch.core.domain.usecase.daily.ScheduleCertifyNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class DailyEditViewModel @Inject constructor(
    private val getDailyManageUseCase: GetDailyManageUseCase,
    private val scheduleCertifyNotificationUseCase: ScheduleCertifyNotificationUseCase,
) : ViewModel() {

    private val _dailyEditUiState = mutableStateOf(DailyEditUiState())
    val dailyEditUiState: State<DailyEditUiState> = _dailyEditUiState

    private val _event: Channel<DailyEditEvent> = Channel()
    val event: Flow<DailyEditEvent> = _event.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        runCatching {
            getDailyManageUseCase("someId") // 임의의 id
        }.onSuccess { dailyManage ->
            _dailyEditUiState.value = DailyEditUiState(
                dailyExpand = dailyManage,
                isLoading = false
            )
        }.onFailure {
            _event.send(DailyEditEvent.LoadError(it))
            it.printStackTrace()
        }
    }

    fun toggleSelectedDay(day: DayOfWeek) {
        _dailyEditUiState.value = dailyEditUiState.value.copy(
            selectedDays = dailyEditUiState.value.selectedDays.toMutableSet().apply {
                if (contains(day)) remove(day) else add(day)
            }
        )
    }

    fun changeSelectedTime(time: LocalTime) {
        _dailyEditUiState.value = _dailyEditUiState.value.copy(
            selectedTime = time
        )
    }

    fun onTimeSelected(time: LocalDateTime) {
        scheduleCertifyNotificationUseCase(time)
    }
}