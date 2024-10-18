package com.teampatch.feature.onboarding.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.teampatch.core.domain.model.FamilyGroup
import com.teampatch.core.domain.model.Host
import com.teampatch.core.domain.model.Image
import com.teampatch.core.domain.model.InvitationMessage
import com.teampatch.core.domain.usecase.onboarding.LoginUseCase
import com.teampatch.core.domain.usecase.onboarding.RegisterFamilyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerFamilyUseCase: RegisterFamilyUseCase
) : ViewModel() {

    // 로그인 시도를 수행하는 함수
    fun login(invitationCode: String): LiveData<Result<FamilyGroup>> = liveData {
        emit(Result.runCatching { loginUseCase(invitationCode) })
    }

    // 가족 등록을 수행하는 함수
    fun registerFamily(
        host: Host,
        relation: String,
        name: String,
        profileImage: Image
    ): LiveData<Result<InvitationMessage>> = liveData {
        emit(Result.runCatching {
            registerFamilyUseCase(
                host = host,
                relation = relation,
                name = name,
                profileImage = profileImage
            )
        })
    }
}