package com.teampatch.core.network

import com.teampatch.core.network.model.group.request.GroupCreationRequestBody
import com.teampatch.core.network.model.group.request.GroupJoinRequestBody
import com.teampatch.core.network.model.group.response.GroupCreationResponse
import com.teampatch.core.network.model.group.response.GroupInviteResponse
import com.teampatch.core.network.model.group.response.GroupJoinResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupRemoteDataSource {

    @POST("/group")
    suspend fun createGroup(
        @Body groupCreationRequestBody: GroupCreationRequestBody,
    ): GroupCreationResponse

    @POST("/group/join")
    suspend fun joinGroup(
        @Body groupJoinRequestBody: GroupJoinRequestBody,
    ): GroupJoinResponse

    @POST("/group/{groupId}/regenerate-invite")
    suspend fun regenerateGroupInviteCode(
        @Path("groupId") groupId: Int,
    ): GroupInviteResponse
}