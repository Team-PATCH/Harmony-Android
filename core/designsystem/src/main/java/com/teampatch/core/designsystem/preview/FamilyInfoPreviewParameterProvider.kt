package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.FamilyInfo
import com.teampatch.core.domain.model.Role

class FamilyInfoPreviewParameterProvider : PreviewParameterProvider<List<FamilyInfo>> {
    override val values: Sequence<List<FamilyInfo>> = sequenceOf(
        listOf(
            FamilyInfo("Grandfather", "John Smith", false, Role.VIP),
            FamilyInfo("Grandmother", "Jane Smith", true, Role.MEMBER),
            FamilyInfo("Father", "Michael Smith", false, Role.MEMBER),
            FamilyInfo("Mother", "Emily Smith", false, Role.MEMBER),
            FamilyInfo("Older Brother", "Daniel Smith", false, Role.MEMBER),
            FamilyInfo("Older Sister", "Laura Smith", false, Role.MEMBER),
            FamilyInfo("Younger Brother", "James Smith", false, Role.MEMBER),
            FamilyInfo("Younger Sister", "Anna Smith", false, Role.MEMBER),
            FamilyInfo("Uncle", "Robert Smith", false, Role.MEMBER),
            FamilyInfo("Aunt", "Elizabeth Smith", false, Role.MEMBER)
        )
    )
}