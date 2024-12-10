package com.teampatch.feature.memory.storage

internal sealed interface MemoryStorageDetailSideEffect {

    data class LoadError(val t: Throwable) : MemoryStorageDetailSideEffect
}