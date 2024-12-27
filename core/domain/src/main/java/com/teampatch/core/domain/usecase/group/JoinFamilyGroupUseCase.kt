package com.teampatch.core.domain.usecase.group

import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import javax.inject.Inject

class JoinFamilyGroupUseCase @Inject constructor(
    private val groupManagementRepository: GroupManagementRepository
) {

    suspend operator fun invoke(inviteCode: String): InvitedGroup {
        return groupManagementRepository.joinFamilyGroup(inviteCode)
    }
}