package com.teampatch.core.data.repository.local

import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.GroupEntity
import com.teampatch.core.domain.model.InvitedGroup
import com.teampatch.core.domain.model.UserGroup
import com.teampatch.core.domain.repository.GroupManagementRepository
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.first

internal class LocalGroupManagementRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val userDao: UserDao,
) : GroupManagementRepository {

    override suspend fun createFamilyGroup(): String {
        val myUserData = userDao.getMyUserData()
        val inviteCode = Random.nextLong(1000, 9999).toString()
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
        TODO("Not yet implemented")
    }

    override suspend fun getUserGroupList(uid: String): List<UserGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun queryGroupInvitationCode(): String {
        TODO("Not yet implemented")
    }
}