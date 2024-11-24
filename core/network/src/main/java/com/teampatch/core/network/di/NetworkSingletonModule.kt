package com.teampatch.core.network.di

import com.teampatch.core.network.BASE_URL
import com.teampatch.core.network.UserRemoteDataSource
import com.teampatch.core.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkSingletonModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .callFactory(okHttpClient)
            .build()
    }

    @Provides
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource {
        return retrofit.create<UserRemoteDataSource>()
    }

}