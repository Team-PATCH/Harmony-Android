package com.teampatch.feature.family.info.model

sealed interface FamilyInfoSideEffect {
    data class Invite(val inviteCode: String) : FamilyInfoSideEffect
    data class InviteError(val t: Throwable) : FamilyInfoSideEffect
    data class LoadError(val t: Throwable) : FamilyInfoSideEffect
}