package com.teampatch.feature.onboarding.make

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.teampatch.core.domain.usecase.group.CreateFamilyGroupUseCase
import com.teampatch.feature.onboarding.make.model.GroupMakingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingMakeRelationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createFamilyGroupUseCase: CreateFamilyGroupUseCase,
) : ViewModel() {

    private val onboardingMakeRelationRoute: OnboardingMakeRelationRoute =
        savedStateHandle.toRoute<OnboardingMakeRelationRoute>()

    private val _isGroupMakingEvent: MutableStateFlow<GroupMakingEvent> = MutableStateFlow(
        GroupMakingEvent.Init
    )
    val isGroupMakingEvent: StateFlow<GroupMakingEvent> = _isGroupMakingEvent.asStateFlow()

    fun createGroup(relation: String, name: String) {
        if (isGroupMakingEvent.value is GroupMakingEvent.Loading) return

        _isGroupMakingEvent.value = GroupMakingEvent.Loading

        viewModelScope.launch {
            try {
                createFamilyGroupUseCase()
                _isGroupMakingEvent.value = GroupMakingEvent.Success
            } catch (e: Exception) {
                e.printStackTrace()
                _isGroupMakingEvent.value = GroupMakingEvent.Error(e)
            }
        }
    }
}