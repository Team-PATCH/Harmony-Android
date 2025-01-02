package com.teampatch.feature.question.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.insertHeaderItem
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.usecase.question.AddQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.DeleteQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.EditQuestionCommentUseCase
import com.teampatch.core.domain.usecase.question.GetQuestionDetailUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.question.detail.mapper.toCommentModel
import com.teampatch.feature.question.detail.mapper.toPostModel
import com.teampatch.feature.question.detail.model.QuestionDetailSideEffect
import com.teampatch.feature.question.detail.model.QuestionDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class QuestionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getQuestionDetailUseCase: GetQuestionDetailUseCase,
    private val addCommentUseCase: AddQuestionCommentUseCase,
    private val editCommentUseCase: EditQuestionCommentUseCase,
    private val deleteCommentUseCase: DeleteQuestionCommentUseCase,
) : ViewModel() {

    private val questionDetailRoute: QuestionDetailRoute = savedStateHandle.toRoute()
    private val questionId: String = questionDetailRoute.questionId

    var uiState = mutableStateOf(QuestionDetailUiState())
        private set

    private val _sideEffect: Channel<QuestionDetailSideEffect> = Channel()
    val sideEffect: Flow<QuestionDetailSideEffect> = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            if (questionId.isEmpty()) {
                Log.d(TAG, "question_id is null")
                return@launch
            }

            val user = getUserInfoUseCase().first()
            val detail = getQuestionDetailUseCase(questionId)

            val post = detail.toPostModel(user.role == Role.VIP)
            val comments = detail.toCommentModel(user.name)
            uiState.value = QuestionDetailUiState(post, comments, false)
        } catch (e: Exception) {
            _sideEffect.send(QuestionDetailSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }

    fun addComment(text: String) = viewModelScope.launch {
        try {
            val questionComment = addCommentUseCase(questionId, text)
            uiState.value = uiState.value.copy(
                comments = uiState.value.comments.map { pagingData ->
                    pagingData.insertHeaderItem(
                        item = QuestionDetailUiState.Comment(
                            id = questionComment.commentId,
                            content = text,
                            writer = QuestionDetailUiState.Comment.Writer(
                                uid = questionComment.writerUid,
                                name = questionComment.writerName
                            ),
                            hasWritePermission = true,
                            isCommentEdited = mutableStateOf(false)
                        )
                    )
                }
            )
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

    companion object {
        private const val TAG = "QuestionDetailViewModel"
    }
}