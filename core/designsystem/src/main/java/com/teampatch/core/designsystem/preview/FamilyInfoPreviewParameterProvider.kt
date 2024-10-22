package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role

class FamilyInfoPreviewParameterProvider : PreviewParameterProvider<List<FamilyInfo>> {
    override val values: Sequence<List<FamilyInfo>> = sequenceOf(
        listOf(
            FamilyInfo(
                title = "Grandfather",
                name = "John Smith",
                isManager = false,
                role = Role.VIP,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Grandmother",
                name = "Jane Smith",
                isManager = true,
                role = Role.MEMBER,
                profileImageUrl = "https://picsum.photos/500/500"
            ),
            FamilyInfo(
                title = "Father",
                name = "Michael Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = "https://picsum.photos/500/500"
            ),
            FamilyInfo(
                title = "Mother",
                name = "Emily Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = "https://picsum.photos/500/500"
            ),
            FamilyInfo(
                title = "Older Brother",
                name = "Daniel Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Older Sister",
                name = "Laura Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Younger Brother",
                name = "James Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Younger Sister",
                name = "Anna Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Uncle",
                name = "Robert Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            ),
            FamilyInfo(
                title = "Aunt",
                name = "Elizabeth Smith",
                isManager = false,
                role = Role.MEMBER,
                profileImageUrl = null
            )
        )
    )
}