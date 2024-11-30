package com.teampatch.core.data.di

import com.teampatch.core.data.repository.MemoryCardRepositoryImpl
import com.teampatch.core.domain.repository.MemoryCardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface DataViewModelModule {

    @Binds
    fun bindMemoryCardRepository(
        impl: MemoryCardRepositoryImpl
    ): MemoryCardRepository
}