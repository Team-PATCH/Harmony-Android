package com.example.daily_manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daily_manage.model.DailyManageSideEffect
import com.example.daily_manage.model.DailyManageUiState
import com.teampatch.core.domain.usecase.daily.AddDailyCommentUseCase
import com.teampatch.core.domain.usecase.daily.DeleteDailyCommentUseCase
import com.teampatch.core.domain.usecase.daily.EditDailyCommentUseCase
import com.teampatch.core.domain.usecase.daily.GetDailyCommentsUseCase
import com.teampatch.core.domain.usecase.daily.GetDailyManageUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DailyManageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getDailyManageUseCase: GetDailyManageUseCase,
    private val getCommentsUseCase: GetDailyCommentsUseCase,
    private val addCommentUseCase: AddDailyCommentUseCase,
    private val editCommentUseCase: EditDailyCommentUseCase,
    private val deleteCommentUseCase: DeleteDailyCommentUseCase,
) : ViewModel() {

    var uiState = mutableStateOf(DailyManageUiState())
        private set

    private val dailyId: StateFlow<String> =
        savedStateHandle.getStateFlow("daily_id", "")

    private val _sideEffect: Channel<DailyManageSideEffect> = Channel()
    val sideEffect: Flow<DailyManageSideEffect> = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            if (dailyId.value.isEmpty()) {
                return@launch
            }
            val user = getUserInfoUseCase().first()
            val daily = getDailyManageUseCase(dailyId.value)
            val comment = getCommentsUseCase(dailyId.value)

            uiState.value = DailyManageUiState(user, daily, comment, false)
        } catch (e: Exception) {
            _sideEffect.send(DailyManageSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }

    fun addComment(text: String) = viewModelScope.launch {
        try {
            addCommentUseCase(dailyId.value, text)
        } catch (e: Exception) {
            _sideEffect.send(DailyManageSideEffect.AddCommentError(e))
            e.printStackTrace()
        }
    }

    fun editComment(commentId: String, text: String) = viewModelScope.launch {
        try {
            editCommentUseCase(commentId, text)
        } catch (e: Exception) {
            _sideEffect.send(DailyManageSideEffect.EditCommentError(e))
            e.printStackTrace()
        }
    }

    fun deleteComment(commentId: String) = viewModelScope.launch {
        try {
            deleteCommentUseCase(commentId)
        } catch (e: Exception) {
            _sideEffect.send(DailyManageSideEffect.DeleteCommentError(e))
            e.printStackTrace()
        }
    }
}