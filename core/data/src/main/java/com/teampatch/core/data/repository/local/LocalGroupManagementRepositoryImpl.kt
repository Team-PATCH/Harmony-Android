package com.teampatch.core.data.repository.local

import com.teampatch.core.database.dao.GroupDao
import com.teampatch.core.database.dao.UserDao
import com.teampatch.core.database.model.GroupEntity
import com.teampatch.core.data.mapper.roleStringMapper
import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.model.UserGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class LocalGroupManagementRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val userDao: UserDao,
) : GroupManagementRepository {

    override suspend fun createFamilyGroup(): String {
        val myUserData = userDao.getMyUserData()
        val inviteCode = Random.nextLong(10000, 99999).toString()
        val groupEntity = GroupEntity(
            id = null,
            vipUid = null,
            managerUid = myUserData.first().uid!!,
            inviteCode = inviteCode
        )
        val groupInsertedIds = groupDao.insertGroups(groupEntity)
        val user = userDao.getMyUserData().first()
        userDao.updateUser(user.copy(groupId = groupInsertedIds[0]))
        return inviteCode
    }

    override suspend fun generateInviteCode(): String {
        val myUserData = userDao.getMyUserData().first()
        val groupEntity = groupDao.queryGroupById(myUserData.groupId!!)
        return groupEntity.first().inviteCode
    }

    override suspend fun joinFamilyGroup(inviteCode: String): InvitedGroup {
        val group = groupDao.queryGroupByInviteCode(inviteCode).first()
        val groupMembers = userDao.getUserByGroupId(group.id!!.toLong()).first()
        val managerUserData = userDao.getUserById(group.managerUid!!).first()

        return InvitedGroup(
            groupId = group.id?.toInt()!!,
            groupManagerInfo = InvitedGroup.GroupManagerInfo(managerUserData.name),
            users = groupMembers.map {
                InvitedGroup.User(it.profileImageUri)
            }
        )
    }

    override suspend fun getUserGroupList(uid: String): List<UserGroup> {
        val userEntity = userDao.getUserById(uid.toLong()).first()
        val userGroupIds = userEntity.groupId
            ?.let { listOf(it) }
            ?: emptyList()

        return userGroupIds.map { groupId ->
            groupDao.queryGroupById(groupId).map { groupEntity ->
                UserGroup(
                    groupId = groupId.toString(),
                    name = "",
                    members = userDao.getUserByGroupId(groupId).map { userEntities ->
                        userEntities.map {
                            FamilyInfo(
                                title = it.relation,
                                name = it.name,
                                isManager = groupEntity.managerUid == it.uid,
                                role = roleStringMapper(it.role),
                                profileImageUrl = it.profileImageUri
                            )
                        }
                    }
                        .first()
                )
            }
                .first()
        }
    }

    override suspend fun queryGroupInvitationCode(): String {
        TODO("Not yet implemented")
    }
}