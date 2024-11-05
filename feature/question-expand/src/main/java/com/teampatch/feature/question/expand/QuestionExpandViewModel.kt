package com.teampatch.feature.question.expand

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.question.GetQuestionsUseCase
import com.teampatch.feature.question.expand.model.QuestionExpandSideEffect
import com.teampatch.feature.question.expand.model.QuestionExpandUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuestionExpandViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    var questionExpandUiState = mutableStateOf(QuestionExpandUiState())
        private set

    private val _sideEffect = Channel<QuestionExpandSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        try {
            val questions = getQuestionsUseCase()
            questionExpandUiState.value = QuestionExpandUiState(question = questions)
        } catch (e: Exception) {
            _sideEffect.send(QuestionExpandSideEffect.LoadError(e))
            e.printStackTrace()
        }
    }
}