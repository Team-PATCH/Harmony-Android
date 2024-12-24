package com.teampatch.core.data.di

import com.teampatch.core.data.entity.TokenManagerImpl
import com.teampatch.core.data.repository.UserRepositoryImpl
import com.teampatch.core.domain.entity.TokenManager
import com.teampatch.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSingletonModule {

    @Binds
    abstract fun bindsTokenManager(
        tokenManagerImpl: TokenManagerImpl
    ): TokenManager

    @Binds
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}