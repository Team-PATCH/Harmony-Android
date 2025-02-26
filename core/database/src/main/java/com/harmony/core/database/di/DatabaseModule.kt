package com.harmony.core.database.di

import android.content.Context
import androidx.room.Room
import com.harmony.core.database.HarmonyDatabase
import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.TodoDao
import com.harmony.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val DB_NAME = "harmony.db"

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    fun providesRoomInstance(
        @ApplicationContext appContext: Context
    ): HarmonyDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = HarmonyDatabase::class.java,
            name = DB_NAME
        )
            .build()
    }

    @Provides
    fun providesTodoDao(
        harmonyDatabase: HarmonyDatabase
    ): TodoDao {
        return harmonyDatabase.todoDao()
    }

    @Provides
    fun providesUserDao(
        harmonyDatabase: HarmonyDatabase
    ): UserDao = harmonyDatabase.userDao()

    @Provides
    fun providesGroupDao(
        harmonyDatabase: HarmonyDatabase
    ): GroupDao = harmonyDatabase.groupDao()
}