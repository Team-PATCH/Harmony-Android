package com.teampatch.feature.home

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.teampatch.core.designsystem.model.CheckableData
import com.teampatch.core.designsystem.toPagingData
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.Todo
import com.teampatch.core.domain.model.User
import com.teampatch.core.domain.usecase.home.AddMemoryCardUseCase
import com.teampatch.core.domain.usecase.home.GetDailyRoutineUseCase
import com.teampatch.core.domain.usecase.home.GetMemoryCardUseCase
import com.teampatch.core.domain.usecase.home.ToggleDailyRoutineStatusUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.home.model.HomeErrorHandler
import com.teampatch.feature.home.model.MemoryCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyRoutineUseCase: GetDailyRoutineUseCase,
    private val getMemoryCardUseCase: GetMemoryCardUseCase,
    private val toggleDailyRoutineStatusUseCase: ToggleDailyRoutineStatusUseCase,
    private val addMemoryCardUseCase: AddMemoryCardUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _errorHandler: MutableSharedFlow<HomeErrorHandler> = MutableSharedFlow()
    val errorHandler: SharedFlow<HomeErrorHandler> = _errorHandler.asSharedFlow()

    val user: StateFlow<User?> = kotlin.runCatching { getUserInfoUseCase() }
        .getOrElse { flowOf(null) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val dailyRoutine: Flow<PagingData<CheckableData<Todo>>> =
        kotlin.runCatching { getDailyRoutineUseCase() }
            .getOrElse { it.toPagingData() }
            .map { pagingData ->
                pagingData.map {
                    CheckableData(it, mutableStateOf(it.isFinished))
                }
            }
            .cachedIn(viewModelScope)

    val memoryCardUiState: StateFlow<MemoryCardUiState> =
        kotlin.runCatching { getMemoryCardUseCase() }
            .map { result ->
                result.map {
                    MemoryCardUiState.Success(it)
                }
            }
            .getOrElse { flowOf(MemoryCardUiState.Error(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MemoryCardUiState.Wait
            )

    fun changeDailyRoutine(routineId: String, checked: Boolean) = viewModelScope.launch {
        try {
            toggleDailyRoutineStatusUseCase(routineId, checked)
        } catch (e: Exception) {
            e.printStackTrace()
            _errorHandler.emit(HomeErrorHandler.ChangeDailyRoutineError(e))
        }
    }

    fun addMemoryCard(
        memories: String,
        dateTime: LocalDateTime,
        image: Uri,
    ) = viewModelScope.launch {
        try {
            addMemoryCardUseCase(
                memories = memories, dateTime = dateTime, image = Image.Uri(image.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            _errorHandler.emit(HomeErrorHandler.MemoryCardAdditionError(e))
        }
    }
}