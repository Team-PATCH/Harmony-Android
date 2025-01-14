package com.teampatch.core.data.repository

import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import com.teampatch.core.domain.repository.UserRepository
import com.teampatch.core.network.GroupRemoteDataSource
import com.teampatch.core.network.model.group.request.GroupCreationRequestBody
import com.teampatch.core.network.model.group.request.GroupJoinRequestBody
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class GroupManagementRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val groupRemoteDataSource: GroupRemoteDataSource,
    private val userRepository: UserRepository,
) : GroupManagementRepository {

    override suspend fun createFamilyGroup(): String {
        val user = userRepository.getUserInfo().first()
        val body = GroupCreationRequestBody(
            userId = user.uid,
            name = user.name,
            deviceToken = tokenManager.getAccessToken()
        )
        val group = groupRemoteDataSource.createGroup(body)
        return group.inviteUrl
    }

    override suspend fun generateInviteCode(): String {
        val user = userRepository.getUserInfo().first()
        val response = groupRemoteDataSource.regenerateGroupInviteCode(user.groupId)
        return response.newInviteCode
    }

    override suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup {
        val user = userRepository.getUserInfo().first()
        val body = GroupJoinRequestBody(
            userId = user.uid,
            inviteCode = inviteCode,
            deviceToken = tokenManager.getAccessToken()
        )
        val response = groupRemoteDataSource.joinGroup(body)
        return response.toDomain()
    }

    /**
     * 이 부분은 논의가 아직 명확히 안돠어 있고 명세에만 있어서 구현체만 만들고 interface는 삭제하였음.
     */
    suspend fun queryUserGroupList(): String {
        val user = userRepository.getUserInfo().first()
        val response = groupRemoteDataSource.queryUserGroupList(user.uid) // groudId는 아닌거같고, 이거 맞는지 모르겠음
        return response.groups.permissionId
    }

    override suspend fun queryGroupInvitationCode(): String {
        val user = userRepository.getUserInfo().first()
        val response = groupRemoteDataSource.queryGroupInvitationCode(user.groupId)
        return response.groupName
    }
}