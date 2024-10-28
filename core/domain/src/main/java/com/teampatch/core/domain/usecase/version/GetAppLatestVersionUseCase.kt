package com.teampatch.core.domain.usecase.version

import com.teampatch.core.domain.model.AppVersion
import kotlinx.coroutines.flow.Flow

interface GetAppLatestVersionUseCase {

    operator fun invoke(): Flow<AppVersion>
}