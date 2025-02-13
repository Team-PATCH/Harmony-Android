package com.teampatch.core.domain.repository

import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.model.UserGroup

interface GroupManagementRepository {

    /**
     * @return Group 초대 URL
     */
    suspend fun createFamilyGroup(): String

    /**
     * Group 초대 코드를 재생성
     * @return Group 초대 코드
     */

    suspend fun generateInviteCode(): String

    suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup

    suspend fun getUserGroupList(uid: String): List<UserGroup>

    /**
     * Group 초대 코드 조회
     * @return Group 초대 코드
     */
    suspend fun queryGroupInvitationCode(): String
}