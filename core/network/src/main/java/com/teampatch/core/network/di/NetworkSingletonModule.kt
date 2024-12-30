package com.teampatch.core.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.teampatch.core.network.BASE_URL
import com.teampatch.core.network.GroupRemoteDataSource
import com.teampatch.core.network.QuestionRemoteDataSource
import com.teampatch.core.network.UserRemoteDataSource
import com.teampatch.core.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkSingletonModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .build()

    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .callFactory(okHttpClient)
        .build()

    @Provides
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource = retrofit.create<UserRemoteDataSource>()

    @Provides
    fun providesQuestionRemoteDataSource(
        retrofit: Retrofit,
    ): QuestionRemoteDataSource = retrofit.create()

    @Provides
    fun providesGroupRemoteDataSource(retrofit: Retrofit): GroupRemoteDataSource = retrofit.create()
}