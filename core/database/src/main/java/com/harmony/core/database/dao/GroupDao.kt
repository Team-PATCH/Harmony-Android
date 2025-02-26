package com.harmony.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harmony.core.database.model.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM `group`")
    fun queryGroups(): Flow<List<GroupEntity>>

    @Query("SELECT * FROM `group` WHERE uid = :uid")
    fun queryGroupByUserId(uid: Long): Flow<List<GroupEntity>>

    @Query("SELECT * FROM `group` WHERE group_id = :groupId")
    fun queryGroupById(groupId: Long): Flow<List<GroupEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM `group` WHERE group_id = :groupId) AS is_present")
    suspend fun queryIsGroupIdPresent(groupId: Long): Boolean

    @Insert
    suspend fun insertGroups(vararg groupEntity: GroupEntity)

    @Query("DELETE FROM `group` WHERE group_id = :groupId")
    fun deleteGroupById(groupId: Long)

    @Delete
    fun deleteGroup(groupEntity: GroupEntity)
}