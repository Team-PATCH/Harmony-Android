package com.teampatch.core.network

import com.teampatch.core.network.di.NetworkSingletonModule
import com.teampatch.core.network.model.group.request.GroupCreationRequestBody
import com.teampatch.core.network.model.group.request.GroupJoinRequestBody
import com.teampatch.core.network.model.group.response.GroupCreationResponse
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import retrofit2.Retrofit

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GroupRemoteDataSourceUnitTest {

    @Test
    fun `ㄱ_Vip가_새로운_그룹을_생성한다`(): Unit = runBlocking {
        val body = GroupCreationRequestBody(
            userId = "yeojeong@naver.com",
            name = "윤여정",
            deviceToken = TestRetrofit.tokenManager.getAccessToken()
        )
        groupCreationResponse = groupRemoteDataSource.createGroup(body)
        println("Vip가_새로운_그룹을_생성한다: ${groupCreationResponse.pretty()}")
    }

    @Test
    fun `ㄴ_그룹을_가입한다`(): Unit = runBlocking {
        val body = GroupJoinRequestBody(
            userId = "yeojeong@naver.com",
            inviteCode = groupCreationResponse.inviteUrl,
            deviceToken = TestRetrofit.tokenManager.getAccessToken()
        )
        val response = groupRemoteDataSource.joinGroup(body)
        println("그룹을_가입한다: ${response.pretty()}")
    }

    @Test
    fun `ㄷ_그룹_초대_코드를_재생성한다`(): Unit = runBlocking {
        val response =
            groupRemoteDataSource.regenerateGroupInviteCode(groupCreationResponse.groupId)
        println("그룹_초대_코드를_재생성한다: ${response.pretty()}")
    }

    companion object {

        private lateinit var retrofit: Retrofit
        private lateinit var groupRemoteDataSource: GroupRemoteDataSource

        private lateinit var groupCreationResponse: GroupCreationResponse

        @JvmStatic
        @BeforeClass
        fun setup() {
            retrofit = TestRetrofit.getRetrofit()
            initUserRemoteDataSource()
        }

        private fun initUserRemoteDataSource() {
            groupRemoteDataSource = NetworkSingletonModule.providesGroupRemoteDataSource(retrofit)
        }
    }
}