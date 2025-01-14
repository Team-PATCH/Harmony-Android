package com.teampatch.core.data.di

import com.teampatch.core.data.repository.AnswerRepositoryImpl
import com.teampatch.core.data.repository.AuthenticationRepositoryImpl
import com.teampatch.core.data.repository.MemoryCardRepositoryImpl
import com.teampatch.core.data.repository.QuestionRepositoryImpl
import com.teampatch.core.domain.repository.AnswerRepository
import com.teampatch.core.domain.repository.AuthenticationRepository
import com.teampatch.core.domain.repository.MemoryCardRepository
import com.teampatch.core.domain.repository.QuestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DataViewModelModule {

    @Binds
    abstract fun bindsMemoryCardRepository(
        memoryCardRepositoryImpl: MemoryCardRepositoryImpl,
    ): MemoryCardRepository

    @Binds
    abstract fun bindsAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl,
    ): AuthenticationRepository

    @Binds
    abstract fun bindsQuestionRepository(
        questionRepositoryImpl: QuestionRepositoryImpl,
    ): QuestionRepository

    @Binds
    abstract fun bindsAnswerRepository(
        answerRepositoryImpl: AnswerRepositoryImpl,
    ): AnswerRepository
}