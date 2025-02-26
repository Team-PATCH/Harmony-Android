package com.harmony.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val pk: Long?,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "group_name") val groupName: String,
    @ColumnInfo(name = "uid") val uid: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_manager") val isManager: Boolean,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "profile_image_uri") val profileImageUri: String?,
)