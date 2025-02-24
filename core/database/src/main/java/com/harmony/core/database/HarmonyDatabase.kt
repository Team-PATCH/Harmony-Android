package com.harmony.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harmony.core.database.dao.TodoDao
import com.harmony.core.database.model.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1,
)
internal abstract class HarmonyDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}