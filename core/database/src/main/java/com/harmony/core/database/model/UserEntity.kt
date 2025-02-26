package com.harmony.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "groupId") val groupId: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "relation") val relation: String,
    @ColumnInfo(name = "profileImageUri") val profileImageUri: String?,
    @ColumnInfo(name = "role") val role: String,
)