package com.teampatch.memorystorage.feature.detail

sealed class MemoryStorageDetailEvent {
    object LoadError : MemoryStorageDetailEvent()
    object Deleted : MemoryStorageDetailEvent()
    data class DeleteError(val message: String) : MemoryStorageDetailEvent()
}