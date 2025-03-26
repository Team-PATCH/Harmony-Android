package com.teampatch.feature.onboarding.make

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingMakeViewModel @Inject constructor() : ViewModel() {
    var relationship by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var profileImageUri by mutableStateOf<Uri?>(null)
        private set

    fun updateRelationship(value: String) {
        relationship = value
    }

    fun updateName(value: String) {
        name = value
    }

    fun updateProfileImage(value: Uri) {
        profileImageUri = value
    }
}