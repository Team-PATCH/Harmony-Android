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

/**
 * DailyEdit를 위한 뷰모델과 Navigation이 덜 완성되었다... usecase도 더 만들어야될거같은데 민준님 도와주세요..
 */
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
            getDailyManageUseCase("someId") // 올바른 dailyId 사용
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

    fun changeDailyContent(content: String) {
        _dailyEditUiState.value = _dailyEditUiState.value.copy(
            dailyExpand = _dailyEditUiState.value.dailyExpand.copy(content = content)
        )
    }

    /** ✅ 요일 선택을 업데이트하는 메서드 추가 **/
    fun toggleSelectedDay(day: DayOfWeek) {
        _dailyEditUiState.value = dailyEditUiState.value.copy(
            selectedDays = dailyEditUiState.value.selectedDays.toMutableSet().apply {
                if (contains(day)) remove(day) else add(day)
            }
        )
    }

    // DailyEditViewModel에 추가
    fun changeSelectedTime(time: LocalTime) {
        _dailyEditUiState.value = _dailyEditUiState.value.copy(
            selectedTime = time
        )
    }

    fun onTimeSelected(time: LocalDateTime) {
        scheduleCertifyNotificationUseCase(time)
    }
}