package com.teampatch.feature.memorycard.registration.model

internal sealed interface MemoryCardRegistrationSideEffect {

    data object LoadError : MemoryCardRegistrationSideEffect
}