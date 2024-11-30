package com.teampatch.core.preferences.di

import com.teampatch.core.preferences.TokenLocalDataSource
import com.teampatch.core.preferences.preferences.EncryptedSharedPreferences
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