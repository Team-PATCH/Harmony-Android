package com.teampatch.daily.certify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DailyCertifyViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(
        DailyCertifyUiState(
//            title = "오늘의 인증 미션",
//            time = LocalTime.now() // 예시
        )
    )
    val uiState: State<DailyCertifyUiState> = _uiState

    fun onDismiss() {
        // 예: 상태 초기화 또는 Analytics 전송 등
    }
}