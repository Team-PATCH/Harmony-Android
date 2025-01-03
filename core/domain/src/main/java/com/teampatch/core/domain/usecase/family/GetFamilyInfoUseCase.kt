package com.teampatch.core.domain.usecase.family

import com.teampatch.core.domain.fake.FakeFamilyInfo
import com.teampatch.core.domain.model.FamilyInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetFamilyInfoUseCase @Inject constructor() {

    operator fun invoke(): Flow<List<FamilyInfo>> = flowOf(FakeFamilyInfo().get())
}