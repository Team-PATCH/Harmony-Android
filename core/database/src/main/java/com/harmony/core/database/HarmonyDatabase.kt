package com.harmony.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
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
        internal const val QUESTION_TABLE_NAME = "question"

        private const val DB_NAME = "harmony.db"
        private var instance: HarmonyDatabase? = null

        fun getInstance(context: Context): HarmonyDatabase {
            val roomDatabaseCallback: Callback = object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    dummyQuestion.forEach { contentValues ->
                        db.insert(
                            table = QUESTION_TABLE_NAME,
                            conflictAlgorithm = OnConflictStrategy.IGNORE,
                            values = contentValues
                        )
                    }
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