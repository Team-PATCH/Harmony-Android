package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.InvitedGroup

interface GroupManagementRepository {

    /**
     * @return Group 초대 URL
     */
    suspend fun createFamilyGroup(): String

    /**
     * @return Group 초대 코드 재생성
     */

    suspend fun generateInviteCode(): String

    suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup

    /**
     * @return Group 초대 코드 조회
     */
    suspend fun queryGroupInvitationCode(): String
}