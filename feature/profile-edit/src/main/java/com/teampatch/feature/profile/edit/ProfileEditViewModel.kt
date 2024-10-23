package com.teampatch.feature.profile.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.usecase.profile.EditProfileUseCase
import com.teampatch.core.domain.usecase.user.GetUserInfoUseCase
import com.teampatch.feature.profile.edit.model.ProfileEditErrorHandler
import com.teampatch.feature.profile.edit.model.ProfileEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _errorHandler: MutableSharedFlow<ProfileEditErrorHandler> = MutableSharedFlow()
    val errorHandler = _errorHandler.asSharedFlow()

    private val _profileEditUiState: MutableStateFlow<ProfileEditUiState> =
        MutableStateFlow(ProfileEditUiState.Init)
    val profileEditUiState = _profileEditUiState.asStateFlow()

    fun loadData() = viewModelScope.launch {
        try {
            val user = getUserInfoUseCase().first()
            _profileEditUiState.value = ProfileEditUiState.Success(
                relation = user.relation,
                name = user.name,
                profileImage = user.profileImageUrl?.let { Image.Url(it) }
            )
        } catch (e: Exception) {
            _profileEditUiState.value = ProfileEditUiState.Error(e)
        }
    }

    fun updateRelation(relation: String) {
        val uiState = profileEditUiState.value as? ProfileEditUiState.Success ?: return
        _profileEditUiState.value = uiState.copy(relation = relation)
    }

    fun updateName(name: String) {
        val uiState = profileEditUiState.value as? ProfileEditUiState.Success ?: return
        _profileEditUiState.value = uiState.copy(name = name)
    }

    fun updateProfileImage(uri: Uri) {
        val uiState = profileEditUiState.value as? ProfileEditUiState.Success ?: return
        _profileEditUiState.value = uiState.copy(profileImage = Image.Uri(uri.toString()))
    }

    fun editProfile() = viewModelScope.launch {
        try {
            val uiState = profileEditUiState.value as? ProfileEditUiState.Success ?: return@launch
            editProfileUseCase(uiState.relation, uiState.name, uiState.profileImage)
        } catch (e: Exception) {
            _errorHandler.emit(ProfileEditErrorHandler.ProfileEditError(e))
        }
    }
}