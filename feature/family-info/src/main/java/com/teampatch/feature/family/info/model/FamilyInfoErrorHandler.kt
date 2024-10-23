package com.teampatch.feature.family.info.model

sealed interface FamilyInfoErrorHandler  {

    data class InviteError(val t: Throwable) : FamilyInfoErrorHandler
}