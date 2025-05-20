package com.teampatch.daily.certify

enum class CertifyStatus {
    BEFORE, // 인증 전
    PENDING, // 인증 중 or 서버 처리 대기 (비활성화 상태 등)
    CONFIRMED, // 인증 완료 (댓글 남기기 가능 상태)
}