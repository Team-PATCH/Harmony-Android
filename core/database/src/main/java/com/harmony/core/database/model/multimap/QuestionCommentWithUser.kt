package com.harmony.core.database.model.multimap

import androidx.room.Embedded
import com.harmony.core.database.model.QuestionCommentEntity
import com.harmony.core.database.model.UserEntity

data class QuestionCommentWithUser(
    @Embedded val comment: QuestionCommentEntity,
    @Embedded val user: UserEntity,
)