package com.teampatch.feature.profile.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.profile.edit.model.ProfileEditSideEffect
import com.teampatch.feature.profile.edit.model.ProfileEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {

    private val _sideEffect: Channel<ProfileEditSideEffect> = Channel()
    val sideEffect: Flow<ProfileEditSideEffect> = _sideEffect.receiveAsFlow()

    private val _profileEditUiState = MutableStateFlow(ProfileEditUiState())
    val profileEditUiState = _profileEditUiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        try {
            val user = getUserInfoUseCase().first()
            _profileEditUiState.value = ProfileEditUiState(
                relation = user.relation,
                name = user.name,
                profileImage = user.profileImageUrl?.let { Image.Url(it) },
                role = user.role,
                isLoading = false
            )
        } catch (e: Exception) {
            _sideEffect.send(ProfileEditSideEffect.LoadError(e))
        }
    }

    fun updateRelation(relation: String) {
        _profileEditUiState.update { it.copy(relation = relation) }
    }

    fun updateName(name: String) {
        _profileEditUiState.update { it.copy(name = name) }
    }

    fun updateProfileImage(uri: Uri) {
        _profileEditUiState.update { it.copy(profileImage = Image.Uri(uri.toString())) }
    }

    fun editProfile() = viewModelScope.launch {
        try {
            val uiState = profileEditUiState.value
            editProfileUseCase(uiState.relation, uiState.name, uiState.profileImage)
            _sideEffect.send(ProfileEditSideEffect.ProfileEditSuccess)
        } catch (e: Exception) {
            _sideEffect.send(ProfileEditSideEffect.ProfileEditError(e))
        }
    }

    override fun onCleared() {
        super.onCleared()
        _sideEffect.close()
    }
}