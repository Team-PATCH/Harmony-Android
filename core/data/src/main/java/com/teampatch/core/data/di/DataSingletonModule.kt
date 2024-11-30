package com.teampatch.core.data.di

import com.teampatch.core.data.manager.TokenManagerImpl
import com.teampatch.core.data.repository.TokenRepositoryImpl
import com.teampatch.core.domain.repository.TokenRepository
import com.teampatch.core.network.utils.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSingletonModule {

    @Binds
    fun bindTokenManager(
        impl: TokenManagerImpl
    ): TokenManager

    @Binds
    fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository

}