package com.teampatch.feature.question.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.question.AddQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.DeleteQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.EditQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.GetQuestionCommentsUseCase
import com.teampatch.core.domain.usecase.question.GetQuestionDetailUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.question.detail.model.QuestionDetailSideEffect
import com.teampatch.feature.question.detail.model.QuestionDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class QuestionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getQuestionDetailUseCase: GetQuestionDetailUseCase,
    private val getCommentsUseCase: GetQuestionCommentsUseCase,
    private val addCommentUseCase: AddQuestionCommentUseCase,
    private val editCommentUseCase: EditQuestionCommentUseCase,
    private val deleteCommentUseCase: DeleteQuestionCommentUseCase,
) : ViewModel() {

    private val questionId: StateFlow<String> =
        savedStateHandle.getStateFlow("question_id", "")

    var uiState = mutableStateOf(QuestionDetailUiState())
        private set

    private val _sideEffect: Channel<QuestionDetailSideEffect> = Channel()
    val sideEffect: Flow<QuestionDetailSideEffect> = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            if (questionId.value.isEmpty()) {
                return@launch
            }

            val user = getUserInfoUseCase().first()
            val detail = getQuestionDetailUseCase(questionId.value)
            val comment = getCommentsUseCase(questionId.value)

            uiState.value = QuestionDetailUiState(user, detail, comment, false)
        } catch (e: Exception) {
            _sideEffect.send(QuestionDetailSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }

    fun addComment(text: String) = viewModelScope.launch {
        try {
            addCommentUseCase(questionId.value, text)
        } catch (e: Exception) {
            _sideEffect.send(QuestionDetailSideEffect.AddCommentError(e))
            e.printStackTrace()
        }
    }

    fun editComment(commentId: String, text: String) = viewModelScope.launch {
        try {
            editCommentUseCase(commentId, text)
        } catch (e: Exception) {
            _sideEffect.send(QuestionDetailSideEffect.EditCommentError(e))
            e.printStackTrace()
        }
    }

    fun deleteComment(commentId: String) = viewModelScope.launch {
        try {
            deleteCommentUseCase(commentId)
        } catch (e: Exception) {
            _sideEffect.send(QuestionDetailSideEffect.DeleteCommentError(e))
            e.printStackTrace()
        }
    }
}