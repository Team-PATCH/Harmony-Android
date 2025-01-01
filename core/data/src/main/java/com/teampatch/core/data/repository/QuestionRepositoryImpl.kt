package com.teampatch.core.data.repository

import androidx.paging.PagingData
import com.teampatch.core.data.mapper.toDomain
import com.teampatch.core.domain.model.Question
import com.teampatch.core.domain.repository.QuestionRepository
import com.teampatch.core.domain.repository.UserRepository
import com.teampatch.core.network.QuestionRemoteDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class QuestionRepositoryImpl @Inject constructor(
    private val questionRemoteDataSource: QuestionRemoteDataSource,
    private val userRepository: UserRepository,
) : QuestionRepository {

    override fun getQuestions(limit: Int): Flow<PagingData<Question>> = flow {
        val user = userRepository.getUserInfo().first()
        val questions = if (limit <= 3) {
            questionRemoteDataSource.getRecentThreeQuestions(user.groupId)
        } else {
            questionRemoteDataSource.getQuestionAll(user.groupId)
        }
            .data
            .let { if (limit > 0) it.subList(0, limit) else it }
            .mapIndexed { index, question ->
                question.toDomain(index)
            }
        emit(PagingData.from(questions))
    }
}