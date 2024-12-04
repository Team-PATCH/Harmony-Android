package com.teampatch.core.data.di

import com.teampatch.core.data.entity.TokenManagerImpl
import com.teampatch.core.data.repository.MemoryCardRepositoryImpl
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.repository.MemoryCardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DataViewModelModule {

    @Binds
    abstract fun bindsMemoryCardRepository(
        memoryCardRepositoryImpl: MemoryCardRepositoryImpl
    ): MemoryCardRepository

    @Binds
    abstract fun bindsTokenManager(
        tokenManagerImpl: TokenManagerImpl
    ): TokenManager
}