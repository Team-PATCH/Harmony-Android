package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User

class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values: Sequence<User> = sequenceOf(
        User("uid001", "Alice Johnson", null, Role.VIP),
        User("uid002", "Bob Smith", null, Role.MEMBER),
        User("uid003", "Charlie Brown", null, Role.VIP),
        User("uid004", "Dana White", null, Role.VIP),
        User("uid005", "Eve Black", null, Role.MEMBER),
        User("uid006", "Frank Green", null, Role.MEMBER),
        User("uid007", "Grace Lee", null, Role.MEMBER),
        User("uid008", "Hank Miller", null, Role.VIP),
        User("uid009", "Ivy Wilson", null, Role.VIP),
        User("uid010", "Jack King", null, Role.VIP)
    )

}