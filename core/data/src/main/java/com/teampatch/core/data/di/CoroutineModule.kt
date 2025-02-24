package com.teampatch.core.data.di

import com.teampatch.core.data.di.annotation.CoroutineDispatcher
import com.teampatch.core.data.di.annotation.DispatcherContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {

    @CoroutineDispatcher(DispatcherContext.Default)
    @Provides
    fun providesDefaultCoroutine(): CoroutineScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.Default)

    @CoroutineDispatcher(DispatcherContext.IO)
    @Provides
    fun providesIoCoroutine(): CoroutineScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.IO)

    @CoroutineDispatcher(DispatcherContext.Main)
    @Provides
    fun providesMainCoroutine(): CoroutineScope =
        CoroutineScope(context = SupervisorJob() + Dispatchers.Main)
}