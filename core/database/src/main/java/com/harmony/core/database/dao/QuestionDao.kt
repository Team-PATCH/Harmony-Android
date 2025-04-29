package com.harmony.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.harmony.core.database.model.QuestionCommentEntity
import com.harmony.core.database.model.QuestionEntity
import com.harmony.core.database.model.multimap.QuestionCommentWithUser
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question limit :limit")
    fun getQuestions(limit: Int): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM question WHERE id = :questionId")
    fun getQuestionById(questionId: Long): Flow<QuestionEntity>

    @Query(
        """
        SELECT question_comment.*, user.*
        FROM question_comment 
        INNER JOIN user ON question_comment.written_uid = user.uid
        WHERE question_comment.question_id = :questionId
        ORDER BY question_comment.modified_at DESC
    """
    )
    fun getQuestionComments(questionId: Long): Flow<List<QuestionCommentWithUser>>

    @Query("SELECT * FROM question_comment WHERE id = :questionCommentId")
    fun getQuestionCommentById(questionCommentId: Long): Flow<QuestionCommentEntity>

    @Insert(QuestionCommentEntity::class)
    suspend fun insertQuestionComment(vararg questionCommentEntity: QuestionCommentEntity): List<Long>

    @Update(QuestionCommentEntity::class)
    suspend fun updateQuestionComment(questionCommentEntity: QuestionCommentEntity)

    @Query("DELETE FROM question_comment WHERE id = :questionCommentId")
    suspend fun deleteQuestionComment(questionCommentId: Long)
}