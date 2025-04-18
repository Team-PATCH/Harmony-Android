package com.teampatch.core.data.di

import com.teampatch.core.data.repository.AnswerRepositoryImpl
import com.teampatch.core.data.repository.AppManagementRepositoryImpl
import com.teampatch.core.data.repository.AuthenticationRepositoryImpl
import com.teampatch.core.data.repository.GroupManagementOfflineRepositoryImpl
import com.teampatch.core.data.repository.GroupManagementRepositoryImpl
import com.teampatch.core.data.repository.MemoryCardRepositoryImpl
import com.teampatch.core.data.repository.QuestionRepositoryImpl
import com.teampatch.core.data.repository.TodoOfflineRepositoryImpl
import com.teampatch.core.data.repository.UserOfflineRepositoryImpl
import com.teampatch.core.data.repository.local.LocalAuthenticationRepositoryImpl
import com.teampatch.core.domain.repository.AnswerRepository
import com.teampatch.core.domain.repository.AppManagementRepository
import com.teampatch.core.domain.repository.AuthenticationRepository
import com.teampatch.core.domain.repository.GroupManagementRepository
import com.teampatch.core.domain.repository.MemoryCardRepository
import com.teampatch.core.domain.repository.QuestionRepository
import com.teampatch.core.domain.repository.TodoRepository
import com.teampatch.core.domain.repository.UserRepository
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
        localAuthenticationRepositoryImpl: LocalAuthenticationRepositoryImpl,
    ): AuthenticationRepository

    @Binds
    abstract fun bindsQuestionRepository(
        questionRepositoryImpl: QuestionRepositoryImpl,
    ): QuestionRepository

    @Binds
    abstract fun bindsAnswerRepository(
        answerRepositoryImpl: AnswerRepositoryImpl,
    ): AnswerRepository

    @Binds
    abstract fun bindsAppManagementRepository(
        appManagementRepositoryImpl: AppManagementRepositoryImpl,
    ): AppManagementRepository

    @Binds
    abstract fun bindsGroupManagementRepository(
        groupManagementOfflineRepositoryImpl: GroupManagementOfflineRepositoryImpl
    ): GroupManagementRepository

    @Binds
    abstract fun bindsTodoRepository(
        todoOfflineRepositoryImpl: TodoOfflineRepositoryImpl
    ): TodoRepository

    @Binds
    abstract fun bindsUserRepository(
        userOfflineRepositoryImpl: UserOfflineRepositoryImpl
    ): UserRepository
}