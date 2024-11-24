package com.teampatch.core.tokenstore.di

import com.teampatch.core.tokenstore.TokenLocalDataSource
import com.teampatch.core.tokenstore.preferences.EncryptedSharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface TokenStoreSingletonModule {

    @Binds
    fun bindTokenStore(
        encryptedSharedPreferences: EncryptedSharedPreferences
    ): TokenLocalDataSource
}