package com.teampatch.core.data.repository

import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import com.teampatch.core.domain.repository.UserRepository
import com.teampatch.core.network.GroupRemoteDataSource
import com.teampatch.core.network.model.group.request.GroupCreationRequestBody
import com.teampatch.core.network.model.group.request.GroupJoinRequestBody
import kotlinx.coroutines.flow.first
import javax.inject.Inject

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

//    override suspend fun queryUserGroupList(): String {
//        val user = userRepository.getUserInfo().first()
//        val response = groupRemoteDataSource.queryUserGroupList(user.uid) // groudId는 아닌거같고, 이거 맞는지 모르겠음
//        return response.groups.permissionId
//    }
    // 이거 override 뜨는거 보니까 joinFamilyGroup()로 참고 해보니 domain usecase도 만들어주어야하네..
}