package com.teampatch.feature.question

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.question.GetQuestionsUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.question.model.QuestionSideEffect
import com.teampatch.feature.question.model.QuestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuestionViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    var questionUiState = mutableStateOf(QuestionUiState())
        private set

    private val _sideEffect = Channel<QuestionSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            val user = getUserInfoUseCase().first()
            val questions = getQuestionsUseCase()
            questionUiState.value = QuestionUiState(user = user, question = questions, isLoading = false)
        } catch (e: Exception) {
            _sideEffect.send(QuestionSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }
}