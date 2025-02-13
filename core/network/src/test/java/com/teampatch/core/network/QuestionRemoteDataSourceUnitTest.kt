package com.teampatch.core.network

import com.teampatch.core.network.di.NetworkSingletonModule
import com.teampatch.core.network.model.question.request.CommentRequestBody
import com.teampatch.core.network.model.question.request.QuestionCardAnswerRequestBody
import com.teampatch.core.network.model.question.request.QuestionCardCommentRequestBody
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Test
import retrofit2.Retrofit

class QuestionRemoteDataSourceUnitTest {

    @Test
    fun `제공된_질문_조회`(): Unit = runBlocking {
        val response = remoteDataSource.getQuestion()
        println("제공된_질문_조회: ${response.pretty()}")
    }

    @Test
    fun `질문_메인뷰에서_오늘의_질문_조회`() = runBlocking {
        val response = remoteDataSource.getTodayQuestion(1)
        println("질문_메인뷰에서_오늘의_질문_조회: ${response.pretty()}")
    }

    @Test
    fun `질문메인뷰에서_최근질문_세개_조회`() = runBlocking {
        val response = remoteDataSource.getRecentThreeQuestions(1)
        println("질문메인뷰에서_최근질문_세개_조회: ${response.pretty()}")
    }

    @Test
    fun `전체_질문_목록_조회하고_답변_저장하고_답변_수정한다`() = runBlocking {
        // 전체 질문 목록 조회
        val response = remoteDataSource.getQuestionAll(1)
        println("전체_질문_목록_조회: ${response.pretty()}")

        // 질문카드_답변_저장
        val answerNullQuestion = response.data.first { it.answer == null }
        val questionCardAnswerRequestBody = QuestionCardAnswerRequestBody("android test")
        val questionCardAnswerResponse = remoteDataSource.postQuestionCardAnswer(
            questionId = answerNullQuestion.questionId,
            questionCardAnswerRequestBody = questionCardAnswerRequestBody
        )
        println("질문카드_답변_저장: ${questionCardAnswerResponse.pretty()}")

        // 질문카드_답변_수정
        val questionCardEditAnswerRequestBody = QuestionCardAnswerRequestBody("android test edit")
        val putQuestionCardAnswer = remoteDataSource.putQuestionCardAnswer(
            questionId = answerNullQuestion.questionId,
            questionCardAnswerRequestBody = questionCardEditAnswerRequestBody
        )
        println("질문카드_답변_수정: ${putQuestionCardAnswer.pretty()}")
    }

    @Test
    fun `질문카드_상세정보_조회한다`() = runBlocking {
        val response = remoteDataSource.getQuestionDetail(1)
        println("질문카드_상세정보_조회: ${response.pretty()}")
    }

    @Test
    fun `질문카드_댓글_조회한다`() = runBlocking {
        val response = remoteDataSource.getQuestionCardComments(1)
        println("질문카드_댓글_조회한다: ${response.pretty()}")
    }

    @Test
    fun `질문카드_댓글을_저장하고_수정하고_삭제한다`() = runBlocking {
        // 댓글 저장
        val questionCardCommentRequestBody = QuestionCardCommentRequestBody(
            questionId = 1,
            groupId = 1,
            authorId = "1",
            content = "android_test"
        )
        val questionCardCommentResponse =
            remoteDataSource.postQuestionCardComment(questionCardCommentRequestBody)
        println("질문카드_코멘트_저장: ${questionCardCommentResponse.pretty()}")

        // 댓글 수정
        val commentRequestBody = CommentRequestBody("android_test_edit")
        val commentEditResponse = remoteDataSource.putComment(
            commentId = questionCardCommentResponse.data.commentId,
            commentRequestBody = commentRequestBody
        )
        println("댓글_수정: ${commentEditResponse.pretty()}")

        // 댓글 삭제
        val commentDeleteResponse =
            remoteDataSource.deleteComment(questionCardCommentResponse.data.commentId)
        println("댓글_삭제: ${commentDeleteResponse.pretty()}")
    }

    companion object {

        private lateinit var retrofit: Retrofit
        private lateinit var remoteDataSource: QuestionRemoteDataSource

        @JvmStatic
        @BeforeClass
        fun setup() {
            retrofit = TestRetrofit.getRetrofit()
            initRemoteDataSource()
        }

        private fun initRemoteDataSource() {
            remoteDataSource = NetworkSingletonModule.providesQuestionRemoteDataSource(retrofit)
        }
    }
}