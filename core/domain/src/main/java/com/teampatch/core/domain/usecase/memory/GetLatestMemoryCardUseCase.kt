package com.teampatch.core.domain.usecase.memory

import com.teampatch.core.domain.model.MemoryCard
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetLatestMemoryCardUseCase @Inject constructor() {

    operator fun invoke(): Flow<MemoryCard> = emptyFlow()
}