package com.teampatch.feature.family.info.model

sealed interface FamilyInfoSideEffect {

    data object Init : FamilyInfoSideEffect

    data object Load : FamilyInfoSideEffect

    data class InviteError(val t: Throwable) : FamilyInfoSideEffect

    data class LoadError(val t: Throwable) : FamilyInfoSideEffect
}