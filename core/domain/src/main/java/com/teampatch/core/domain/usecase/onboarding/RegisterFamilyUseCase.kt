package com.teampatch.core.domain.usecase.onboarding

import com.teampatch.core.domain.model.InvitationMessage
import com.teampatch.core.domain.model.Host
import com.teampatch.core.domain.model.Image

interface RegisterFamilyUseCase {

    /**
     * @param host 할머니 혹은 할아버지
     * @param relation [host]와 관계 예) 손녀
     * @param name [host]에게 보여지는 이름
     * @param profileImage [host]에게 보여지는 프로필 이미지
     */

    suspend operator fun invoke(
        host: Host,
        relation: String,
        name: String,
        profileImage: Image,
    ): InvitationMessage
}

