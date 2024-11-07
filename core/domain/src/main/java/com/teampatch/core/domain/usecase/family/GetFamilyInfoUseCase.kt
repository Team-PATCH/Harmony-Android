package com.teampatch.core.domain.usecase.family

import com.teampatch.core.domain.model.FamilyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetFamilyInfoUseCase @Inject constructor() {

    operator fun invoke(): Flow<List<FamilyInfo>> = emptyFlow()
}