package com.teampatch.core.domain.usecase.version

import com.teampatch.core.domain.model.AppVersion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetAppLatestVersionUseCase @Inject constructor() {

    operator fun invoke(): Flow<AppVersion> = emptyFlow()
}