package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.InvitedGroup

interface GroupManagementRepository {

    /**
     * @return Group 초대 URL
     */
    suspend fun createFamilyGroup(): String

    /**
     * @return Group 초대 코드
     */

    suspend fun generateInviteCode(): String

    suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup
}