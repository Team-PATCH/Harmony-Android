package com.harmony.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "epoch_milli") val epochMilli: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_finished") val isFinished: Boolean
)