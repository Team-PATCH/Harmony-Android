package com.teampatch.memorystorage.feature.detail

internal sealed interface MemoryStorageDetailEvent {
    data class LoadError(val t: Throwable) : MemoryStorageDetailEvent
}