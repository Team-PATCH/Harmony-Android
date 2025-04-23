package com.teampatch.feature.onboarding.make

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class OnboardingMakeViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
) : ViewModel() {
    var relationship by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri.asStateFlow()

    fun updateRelationship(value: String) {
        relationship = value
    }

    fun updateName(value: String) {
        name = value
    }

    fun updateProfileImage(value: Uri) {
        _profileImageUri.value = value
    }

    override fun onCleared() {
        profileImageUri.value?.let {
            viewModelScope.launch {
                editProfileUseCase(null, it.toString())
            }
        }
        super.onCleared()
    }
}