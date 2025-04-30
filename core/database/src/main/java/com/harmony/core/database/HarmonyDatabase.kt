package com.harmony.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.QuestionDao
import com.harmony.core.database.dao.TodoDao
import com.harmony.core.database.dao.UserDao
import com.harmony.core.database.model.GroupEntity
import com.harmony.core.database.model.QuestionCommentEntity
import com.harmony.core.database.model.QuestionEntity
import com.harmony.core.database.model.TodoEntity
import com.harmony.core.database.model.UserEntity
import com.harmony.core.database.model.preload.QuestionPreloadData
import com.harmony.core.database.model.preload.TodoPreloadData

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
internal abstract class HarmonyDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun questionDao(): QuestionDao

    companion object {
        private const val DB_NAME = "harmony.db"

        @Volatile
        private var instance: HarmonyDatabase? = null

        fun getInstance(context: Context): HarmonyDatabase {
            val roomDatabaseCallback: Callback = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    listOf(
                        QuestionPreloadData(),
                        TodoPreloadData()
                    )
                        .forEach { it.insertPreloadData(db) }
                }
            }

            return instance ?: synchronized(HarmonyDatabase::class) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    HarmonyDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(roomDatabaseCallback)
                    .build()
                    .also { instance = it }
            }
        }
    }
}