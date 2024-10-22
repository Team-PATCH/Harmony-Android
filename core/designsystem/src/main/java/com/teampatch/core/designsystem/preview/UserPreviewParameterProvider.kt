package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.Role
import com.teampatch.core.domain.model.User

class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values: Sequence<User> = sequenceOf(
        User("uid001", "Alice Johnson", "http://example.com/images/alice.jpg", Role.VIP),
        User("uid002", "Bob Smith", "http://example.com/images/bob.jpg", Role.MEMBER),
        User("uid003", "Charlie Brown", null, Role.VIP),
        User("uid004", "Dana White", "http://example.com/images/dana.jpg", Role.VIP),
        User("uid005", "Eve Black", null, Role.MEMBER),
        User("uid006", "Frank Green", "http://example.com/images/frank.jpg", Role.MEMBER),
        User("uid007", "Grace Lee", "http://example.com/images/grace.jpg", Role.MEMBER),
        User("uid008", "Hank Miller", null, Role.VIP),
        User("uid009", "Ivy Wilson", "http://example.com/images/ivy.jpg", Role.VIP),
        User("uid010", "Jack King", "http://example.com/images/jack.jpg", Role.VIP)
    )

}