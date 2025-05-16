package com.teampatch.core.data.di

import com.teampatch.core.data.di.annotation.DispatchersContext
import com.teampatch.core.data.di.annotation.HarmonyDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {

    @HarmonyDispatcher(DispatchersContext.Default)
    @Provides
    fun providesDefaultCoroutine(): CoroutineDispatcher = Dispatchers.Default

    @HarmonyDispatcher(DispatchersContext.IO)
    @Provides
    fun providesIoCoroutine(): CoroutineDispatcher = Dispatchers.IO
}