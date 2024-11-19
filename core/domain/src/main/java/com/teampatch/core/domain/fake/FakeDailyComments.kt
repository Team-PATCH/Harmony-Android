package com.teampatch.core.domain.fake

import com.teampatch.core.domain.model.DailyComment

class FakeDailyComments : FakeModel<List<DailyComment>>() {

    override fun build(): List<DailyComment> = listOf(
        DailyComment(
            commentId = "cmt001",
            writerUid = "uid001",
            writerName = "Alice Johnson",
            content = "Great question!"
        ),
        DailyComment(
            commentId = "cmt002",
            writerUid = "uid002",
            writerName = "Bob Smith",
            content = "I’ve been wondering the same."
        ),
        DailyComment(
            commentId = "cmt003",
            writerUid = "uid003",
            writerName = "Charlie Brown",
            content = "Here’s my take on it."
        ),
        DailyComment(
            commentId = "cmt004",
            writerUid = "uid004",
            writerName = "Dana White",
            content = "Very insightful."
        ),
        DailyComment(
            commentId = "cmt005",
            writerUid = "uid005",
            writerName = "Eve Black",
            content = "Thanks for asking!"
        ),
        DailyComment(
            commentId = "cmt006",
            writerUid = "uid006",
            writerName = "Frank Green",
            content = "Interesting perspective."
        ),
        DailyComment(
            commentId = "cmt007",
            writerUid = "uid007",
            writerName = "Grace Lee",
            content = "I completely agree."
        ),
        DailyComment(
            commentId = "cmt008",
            writerUid = "uid008",
            writerName = "Hank Miller",
            content = "I’ve never thought about that."
        ),
        DailyComment(
            commentId = "cmt009",
            writerUid = "uid009",
            writerName = "Ivy Wilson",
            content = "That’s a good point."
        ),
        DailyComment(
            commentId = "cmt010",
            writerUid = "uid010",
            writerName = "Jack King",
            content = "Here are my thoughts on this."
        )
    )
}