package com.teampatch.core.domain.usecase.version

import com.teampatch.core.domain.model.AppVersion
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetAppLatestVersionUseCase @Inject constructor() {

    operator fun invoke(): Flow<AppVersion> = emptyFlow()
}