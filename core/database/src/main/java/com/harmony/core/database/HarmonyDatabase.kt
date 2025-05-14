package com.harmony.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harmony.core.database.converters.SetStringTypeTypeConverter
import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.QuestionDao
import com.harmony.core.database.dao.TodoDao
import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.GroupEntity
import com.harmony.core.database.model.QuestionCommentEntity
import com.harmony.core.database.model.QuestionEntity
import com.harmony.core.database.model.TodoEntity
import com.harmony.core.database.model.UserEntity

@Database(
    entities = [
        TodoEntity::class,
        UserEntity::class,
        GroupEntity::class,
        QuestionEntity::class,
        QuestionCommentEntity::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        SetStringTypeTypeConverter::class
    ]
)
internal abstract class HarmonyDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun questionDao(): QuestionDao

    companion object {
        internal const val DB_NAME = "harmony.db"
    }
}