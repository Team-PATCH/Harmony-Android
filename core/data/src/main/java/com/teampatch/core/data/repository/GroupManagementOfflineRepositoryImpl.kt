package com.teampatch.core.data.repository

import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.GroupEntity
import com.teampatch.core.data.mapper.roleStringMapper
import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.model.UserGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class GroupManagementOfflineRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val userDao: UserDao
) : GroupManagementRepository {

    override suspend fun createFamilyGroup(): String {
        val userEntity = userDao.getMyUserData().first()
        var groupId: Long

        do {
            groupId = Random.nextLong(Long.MIN_VALUE, Long.MAX_VALUE)
        } while (!groupDao.queryIsGroupIdPresent(groupId))

        groupDao.insertGroups(
            GroupEntity(
                pk = null,
                groupId = groupId,
                groupName = "${userEntity.name}'s group",
                uid = userEntity.uid,
                title = userEntity.relation,
                name = userEntity.name,
                isManager = true,
                role = userEntity.role,
                profileImageUri = userEntity.profileImageUri,
            )
        )

        return groupId.toString()
    }

    override suspend fun generateInviteCode(): String {
        val userEntity = userDao.getMyUserData().first()
        return userEntity.groupId.toString()
    }

    override suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup {
        val groupEntities = groupDao.queryGroupById(inviteCode.toLong())

        return InvitedGroup(
            groupId = inviteCode.toInt(),
            groupManagerInfo = InvitedGroup.GroupManagerInfo(""),
            users = groupEntities.first().map { InvitedGroup.User(it.profileImageUri) }
        )
    }

    override suspend fun getUserGroupList(uid: String): List<UserGroup> {
        return groupDao.queryGroupByUserId(uid.toLong())
            .map { groupEntities ->
                groupEntities
                    .groupBy { it.groupId }
                    .map { entry ->
                        UserGroup(
                            groupId = entry.key.toString(),
                            name = entry.value.first().name,
                            members = entry.value.map { groupEntity ->
                                FamilyInfo(
                                    title = groupEntity.title,
                                    name = groupEntity.name,
                                    isManager = groupEntity.isManager,
                                    role = roleStringMapper(groupEntity.role),
                                    profileImageUrl = groupEntity.profileImageUri
                                )
                            }
                        )
                    }
            }
            .first()
    }

    override suspend fun queryGroupInvitationCode(): String {
        return generateInviteCode()
    }
}