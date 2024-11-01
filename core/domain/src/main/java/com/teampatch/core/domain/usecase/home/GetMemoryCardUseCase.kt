package com.teampatch.core.domain.usecase.home

import com.teampatch.core.domain.model.MemoryCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetMemoryCardUseCase @Inject constructor(){

    operator fun invoke(): Flow<MemoryCard> {
        return emptyFlow()
    }
}