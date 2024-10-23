package com.teampatch.core.domain.usecase.family

import com.teampatch.core.domain.model.FamilyInfo
import kotlinx.coroutines.flow.Flow

interface GetFamilyInfoUseCase {

    operator fun invoke(): Flow<List<FamilyInfo>>
}