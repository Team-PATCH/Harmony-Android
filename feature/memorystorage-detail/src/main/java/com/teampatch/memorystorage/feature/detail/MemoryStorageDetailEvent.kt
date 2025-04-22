package com.teampatch.memorystorage.feature.detail

sealed class MemoryStorageDetailEvent {
    object LoadError : MemoryStorageDetailEvent()
}