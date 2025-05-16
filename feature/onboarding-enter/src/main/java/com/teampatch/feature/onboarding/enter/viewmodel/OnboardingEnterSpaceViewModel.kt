package com.teampatch.feature.onboarding.enter.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingEnterSpaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _profileImageUris = mutableStateOf<List<Uri>>(emptyList())
    val profileImageUris: State<List<Uri>> = _profileImageUris

    init {
        // NavController.navigateToEnterSpaceScreen 에서 사용한 Route data class의 프로퍼티 이름과 동일해야 함
        val uriStrings: Array<String>? = savedStateHandle.get<Array<String>>("profileImageUrisAsStrings")
        Log.d("OnboardingEnterSpaceVM", "Received URI strings from NavArgs: ${uriStrings?.toList()}")

        if (uriStrings != null) {
            try {
                _profileImageUris.value = uriStrings.mapNotNull { stringUri ->
                    try {
                        Uri.parse(stringUri)
                    } catch (e: Exception) {
                        Log.e("OnboardingEnterSpaceVM", "Failed to parse URI string: $stringUri", e)
                        null // 파싱 실패 시 null 반환하여 filterNotNull 등으로 걸러낼 수 있음
                    }
                }
                Log.d("OnboardingEnterSpaceVM", "Parsed URIs: ${_profileImageUris.value}")
            } catch (e: Exception) {
                Log.e("OnboardingEnterSpaceVM", "Error processing URI strings", e)
                _profileImageUris.value = emptyList() // 오류 발생 시 빈 리스트로 초기화
            }
        } else {
            Log.d("OnboardingEnterSpaceVM", "No URI strings found in NavArgs.")
            _profileImageUris.value = emptyList() // null인 경우 빈 리스트로 초기화
        }
    }
}