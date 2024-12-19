package com.teampatch.core.domain.usecase.group

import com.teampatch.core.domain.model.InvitationMessage
import com.teampatch.core.domain.repository.GroupManagementRepository
import javax.inject.Inject

class CreateFamilyGroupUseCase @Inject constructor(
    private val groupManagementRepository: GroupManagementRepository
) {

    suspend operator fun invoke(): InvitationMessage {
        val invitationUrl = groupManagementRepository.createFamilyGroup()
        val invitationCode = groupManagementRepository.generateInviteCode()
        return InvitationMessage(url = invitationUrl, code = invitationCode)
    }
}