package com.harmony.core.database.di

import android.content.Context
import com.harmony.core.database.HarmonyDatabase
import com.harmony.core.database.dao.GroupDao
import com.harmony.core.database.dao.TodoDao
import com.harmony.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun providesRoomInstance(
        @ApplicationContext appContext: Context,
    ): HarmonyDatabase = HarmonyDatabase.getInstance(appContext)

    @Provides
    fun providesTodoDao(
        harmonyDatabase: HarmonyDatabase,
    ): TodoDao = harmonyDatabase.todoDao()

    @Provides
    fun providesUserDao(
        harmonyDatabase: HarmonyDatabase,
    ): UserDao = harmonyDatabase.userDao()

    @Provides
    fun providesGroupDao(
        harmonyDatabase: HarmonyDatabase,
    ): GroupDao = harmonyDatabase.groupDao()
}