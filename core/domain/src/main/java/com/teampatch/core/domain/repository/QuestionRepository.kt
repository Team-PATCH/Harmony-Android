package com.teampatch.core.domain.repository

import androidx.paging.PagingData
import com.teampatch.core.domain.model.Question
import com.teampatch.core.domain.model.QuestionDetail
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun getQuestions(limit: Int = 3): Flow<PagingData<Question>>

    suspend fun getQuestionDetail(questionId: String): QuestionDetail

    suspend fun addComment(questionId: String, comment: String)

    suspend fun editComment(commentId: String, comment: String)

    suspend fun deleteComment(commentId: String)
}