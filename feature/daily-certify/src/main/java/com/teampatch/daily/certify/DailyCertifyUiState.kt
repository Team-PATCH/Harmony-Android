package com.teampatch.daily.certify

import com.teampatch.core.domain.model.DailyComment
import java.time.LocalTime

internal data class DailyCertifyUiState(
    val missionText: String = "",
    val missionTime: LocalTime? = null,
    val imageUrl: String? = null, // (이미지가 URL인 경우)
    val certifyStatus: CertifyStatus = CertifyStatus.BEFORE,
    val comments: List<DailyComment> = emptyList(),
    val editingComment: DailyComment? = null, // 수정 중인 댓글이 있으면 bottomSheet 띄움
)