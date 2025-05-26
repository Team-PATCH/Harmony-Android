package com.teampatch.daily.certify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.teampatch.core.domain.model.DailyComment
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
internal class DailyCertifyViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(
        DailyCertifyUiState(
            missionText = "오늘의 인증 미션",
            missionTime = LocalTime.of(14, 0), // 예시 시간
            certifyStatus = CertifyStatus.PENDING,
            comments = listOf(
                DailyComment("1", "소금형", "씹직", "고양이 귀엽네"),
                DailyComment("2", "짱구맘", "인직", "나도 사진 찍을래!")
            )
        )
    )
    val uiState: State<DailyCertifyUiState> = _uiState

    fun completeCertify() {
        _uiState.value = _uiState.value.copy(
            certifyStatus = CertifyStatus.CONFIRMED
        )
    }

    fun editComment(comment: DailyComment?) {
        _uiState.value = _uiState.value.copy(
            editingComment = comment
        )
    }

    fun openCommentSheet() {
        _uiState.value = _uiState.value.copy(
            showCommentSheet = true
        )
    }

    fun onDismiss() {
        // TODO: 인증 완료 종료 시 처리할 것
    }
}