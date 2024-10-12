package com.teampatch.core.domain.model

data class Host(
    val title: Title,
    val name: String,
) {
    enum class Title {
        GRANDFATHER, GRANDMOTHER
    }
}