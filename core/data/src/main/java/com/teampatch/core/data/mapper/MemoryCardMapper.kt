package com.teampatch.core.data.mapper

import com.teampatch.core.database.LOCAL_DB_DATE_TIME_FORMATTER
import com.teampatch.core.database.model.MemoryCardEntity
import com.teampatch.core.domain.model.MemoryCard
import java.time.LocalDateTime

fun MemoryCardEntity.toDomain(
    writerName: String,
): MemoryCard = MemoryCard(
    id = id.toString(),
    writerTitle = "",
    writerName = writerName,
    text = title,
    imageUrl = imageUrl,
    dateTime = LocalDateTime.parse(
        /* text = */
        modifiedAt,
        /* formatter = */
        LOCAL_DB_DATE_TIME_FORMATTER
    )
)