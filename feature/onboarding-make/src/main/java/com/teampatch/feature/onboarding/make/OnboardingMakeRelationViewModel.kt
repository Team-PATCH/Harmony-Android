package com.teampatch.feature.onboarding.make

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.domain.usecase.group.CreateFamilyGroupUseCase
import com.teampatch.feature.onboarding.make.model.GroupMakingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class OnboardingMakeRelationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createFamilyGroupUseCase: CreateFamilyGroupUseCase,
) : ViewModel() {

    private val onboardingMakeRelationRoute: OnboardingMakeRelationRoute =
        savedStateHandle.toRoute<OnboardingMakeRelationRoute>()

    private val _isGroupMakingEvent: Channel<GroupMakingEvent> = Channel()
    val isGroupMakingEvent: Flow<GroupMakingEvent> = _isGroupMakingEvent.receiveAsFlow()

    private var isCreatingGroup: Boolean = false

    fun createGroup(relation: String, name: String) = viewModelScope.launch {
        try {
            if (isCreatingGroup) {
                _isGroupMakingEvent.send(GroupMakingEvent.Progress)
                return@launch
            }
            isCreatingGroup = true
            createFamilyGroupUseCase()
            _isGroupMakingEvent.send(GroupMakingEvent.Success)
        } catch (e: Exception) {
            e.printStackTrace()
            _isGroupMakingEvent.send(GroupMakingEvent.Error(e))
        }
    }
        .invokeOnCompletion {
            isCreatingGroup = false
        }
}