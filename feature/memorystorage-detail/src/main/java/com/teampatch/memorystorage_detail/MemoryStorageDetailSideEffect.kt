package com.teampatch.memorystorage_detail

internal sealed interface MemoryStorageDetailSideEffect {

    data object LoadError : MemoryStorageDetailSideEffect
}