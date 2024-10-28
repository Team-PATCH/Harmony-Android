package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User

class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values: Sequence<User> = sequenceOf(
        User("uid001", "Alice Johnson", "Mother", null, Role.VIP),
        User("uid002", "Bob Smith", "Father", null, Role.MEMBER),
        User("uid003", "Charlie Brown", "Older Brother", null, Role.MEMBER),
        User("uid004", "Dana White", "Older Sister", null, Role.MEMBER),
        User("uid005", "Eve Black", "Younger Sister", null, Role.MEMBER),
        User("uid006", "Frank Green", "Younger Brother", null, Role.MEMBER),
        User("uid007", "Grace Lee", "Aunt", null, Role.MEMBER),
        User("uid008", "Hank Miller", "Uncle", null, Role.MEMBER),
        User("uid009", "Ivy Wilson", "Grandmother", null, Role.MEMBER),
        User("uid010", "Jack King", "Grandfather", null, Role.MEMBER)
    )

}