package com.teampatch.core.domain.usecase.home

import com.teampatch.core.domain.model.MemoryCard
import kotlinx.coroutines.flow.Flow

interface GetMemoryCardUseCase {

    operator fun invoke(): Flow<MemoryCard>
}