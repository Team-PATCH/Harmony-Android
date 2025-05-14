package com.harmony.core.database.model.preload

import android.content.ContentValues

internal class MemoryCardPreloadData : DataPreloadHelper() {

    companion object {
        private const val TABLE_NAME = "memory_card"
    }

    override val tableName: String = TABLE_NAME

    override val preloadData: List<ContentValues> = listOf(
        ContentValues().apply {
            put("question_id", 1)
            put("written_uid", 101)
            put("title", "할머니는 어릴 때 어떤 놀이를 가장 좋아하셨어요?")
            put("created_at", "2024-06-12 14:30:00")
            put("modified_at", "2024-06-12 14:45:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"추억\",\"놀이\"]")
        },
        ContentValues().apply {
            put("question_id", 2)
            put("written_uid", 102)
            put("title", "어릴 때 가장 기억에 남는 음식은 뭐예요?")
            put("created_at", "2024-06-13 10:15:00")
            put("modified_at", "2024-06-13 10:30:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"음식\",\"기억\"]")
        },
        ContentValues().apply {
            put("question_id", 3)
            put("written_uid", 103)
            put("title", "가장 소중한 추억은 언제인가요?")
            put("created_at", "2024-06-14 16:45:00")
            put("modified_at", "2024-06-14 17:00:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"추억\",\"기억\"]")
        },
        ContentValues().apply {
            put("question_id", 4)
            put("written_uid", 104)
            put("title", "예전에는 어떤 음악을 즐겨 들으셨나요?")
            put("created_at", "2024-06-15 09:20:00")
            put("modified_at", "2024-06-15 09:35:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"음악\",\"옛날\"]")
        },
        ContentValues().apply {
            put("question_id", 5)
            put("written_uid", 105)
            put("title", "가장 기억에 남는 여행지는 어디인가요?")
            put("created_at", "2024-06-16 14:00:00")
            put("modified_at", "2024-06-16 14:15:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"여행\",\"기억\"]")
        },
        ContentValues().apply {
            put("question_id", 6)
            put("written_uid", 106)
            put("title", "어릴 때 가장 좋아했던 책은 무엇인가요?")
            put("created_at", "2024-06-17 20:30:00")
            put("modified_at", "2024-06-17 20:45:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"책\",\"추억\"]")
        },
        ContentValues().apply {
            put("question_id", 7)
            put("written_uid", 107)
            put("title", "예전에는 어떤 직업을 꿈꾸셨나요?")
            put("created_at", "2024-06-18 08:00:00")
            put("modified_at", "2024-06-18 08:20:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"직업\",\"꿈\"]")
        },
        ContentValues().apply {
            put("question_id", 8)
            put("written_uid", 108)
            put("title", "젊었을 때 가장 즐겼던 취미는 무엇인가요?")
            put("created_at", "2024-06-19 11:50:00")
            put("modified_at", "2024-06-19 12:10:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"취미\",\"기억\"]")
        },
        ContentValues().apply {
            put("question_id", 9)
            put("written_uid", 109)
            put("title", "어릴 때 가장 무서웠던 경험은 무엇인가요?")
            put("created_at", "2024-06-20 17:10:00")
            put("modified_at", "2024-06-20 17:30:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"공포\",\"추억\"]")
        },
        ContentValues().apply {
            put("question_id", 10)
            put("written_uid", 110)
            put("title", "가장 존경하는 인물은 누구인가요?")
            put("created_at", "2024-06-21 22:40:00")
            put("modified_at", "2024-06-21 22:55:00")
            put("image_url", "https://picsum.photos/400")
            put("tags", "[\"존경\",\"인물\"]")
        }
    )
}