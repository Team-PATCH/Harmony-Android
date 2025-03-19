package com.teampatch.feature.memorystorage

sealed class MemoryStorageEvent {
    object LoadError : MemoryStorageEvent()
}