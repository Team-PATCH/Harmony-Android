package com.teampatch.daily.certify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.teampatch.core.domain.model.DailyComment
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DailyCertifyViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(
        DailyCertifyUiState(
            missionText = "오늘의 인증 미션",
            missionTime = LocalTime.of(14, 0), // 예시 시간,
            imageUrl = null, // ✅ 중요
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

    fun deleteComment(comment: DailyComment?) {
        _uiState.value = _uiState.value.copy(
            comments = _uiState.value.comments.filterNot { it.commentId == comment?.commentId }
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

    fun addComment(content: String, imageUrl: String?) {
        val newComment = DailyComment(
            commentId = UUID.randomUUID().toString(),
            writerName = "작성자",
            writerUid = "",
            content = content,
            imageUrl = imageUrl,
            profileImageUrl = imageUrl // ✅ 첨부한 이미지를 프로필로도 활용
        )
        _uiState.value = _uiState.value.copy(
            comments = _uiState.value.comments + newComment,
            showCommentSheet = false
        )
    }


    fun updateComment(commentId: String, newContent: String) {
        _uiState.value = _uiState.value.copy(
            comments = _uiState.value.comments.map {
                if (it.commentId == commentId) it.copy(content = newContent) else it
            },
            editingComment = null
        )
    }

    fun closeCommentSheet() {
        _uiState.value = _uiState.value.copy(
            showCommentSheet = false,
            editingComment = null
        )
    }



    fun updateImage(uri: String) {
        _uiState.value = _uiState.value.copy(imageUrl = uri)
    }
}