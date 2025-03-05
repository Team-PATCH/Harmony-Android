package com.teampatch.feature.daily.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
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
    savedStateHandle: SavedStateHandle,
    private val getDailyManageUseCase: GetDailyManageUseCase,
) : ViewModel() {
    private val dailyEditRoute = savedStateHandle.toRoute<DailyEditRoute>()

    var dailyEditUiState = mutableStateOf(DailyEditUiState())
        private set

    private val _sideEffect: Channel<DailyEditSideEffect> = Channel()
    val sideEffect: Flow<DailyEditSideEffect> = _sideEffect.receiveAsFlow()

    var selectedDays = mutableStateOf(setOf<String>())
        private set

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            if (dailyEditRoute.dailyId.isEmpty()) return@launch

            val expand = getDailyManageUseCase(dailyEditRoute.dailyId)
            dailyEditUiState.value = DailyEditUiState(
                dailyExpand = expand,
                isLoading = false
            )
            selectedDays.value = setOf(expand.dateTime.dayOfWeek.name)
        } catch (e: Exception) {
            _sideEffect.send(DailyEditSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }

    fun toggleDaySelection(day: String) {
        selectedDays.value = if (selectedDays.value.contains(day)) {
            selectedDays.value - day
        } else {
            selectedDays.value + day
        }
    }

    fun saveDaily(daily: String) = viewModelScope.launch {
        // UseCase를 호출하여 선택한 요일과 함께 저장
    }
}