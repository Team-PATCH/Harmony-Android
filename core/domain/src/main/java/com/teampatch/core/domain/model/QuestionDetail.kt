package com.teampatch.core.domain.model

import androidx.paging.PagingData
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class QuestionDetail(
    val id: String,
    val number: Int,
    val title: String,
    val content: String,
    val dateTime: LocalDateTime,
    val commentCount: Int,
    val comment: Flow<PagingData<QuestionComment>> = flowOf(PagingData.empty()),
)