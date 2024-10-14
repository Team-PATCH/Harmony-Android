package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.model.Todo
import java.time.LocalDateTime

class TodoPreviewParameterProvider : PreviewParameterProvider<List<Todo>> {

    override val values: Sequence<List<Todo>> = sequenceOf(
        listOf(
            Todo(
                id = "1",
                dateTime = LocalDateTime.of(2023, 9, 12, 14, 30),
                title = "Buy groceries",
                isFinished = false
            ),
            Todo(
                id = "2",
                dateTime = LocalDateTime.of(2023, 9, 12, 16, 0),
                title = "Meeting with team",
                isFinished = true
            ),
            Todo(
                id = "3",
                dateTime = LocalDateTime.of(2023, 9, 13, 10, 0),
                title = "Gym session",
                isFinished = false
            ),
            Todo(
                id = "4",
                dateTime = LocalDateTime.of(2023, 9, 13, 9, 0),
                title = "Doctor appointment",
                isFinished = true
            ),
            Todo(
                id = "5",
                dateTime = LocalDateTime.of(2023, 9, 13, 12, 0),
                title = "Lunch with friends",
                isFinished = false
            )
        ),
        listOf(
            Todo(
                id = "6",
                dateTime = LocalDateTime.of(2023, 9, 17, 15, 30),
                title = "Study session",
                isFinished = false
            ),
            Todo(
                id = "7",
                dateTime = LocalDateTime.of(2023, 9, 18, 8, 0),
                title = "Morning jog",
                isFinished = true
            ),
            Todo(
                id = "8",
                dateTime = LocalDateTime.of(2023, 9, 19, 14, 0),
                title = "Client call",
                isFinished = false
            ),
            Todo(
                id = "9",
                dateTime = LocalDateTime.of(2023, 9, 20, 11, 0),
                title = "Visit library",
                isFinished = false
            ),
            Todo(
                id = "10",
                dateTime = LocalDateTime.of(2023, 9, 21, 19, 0),
                title = "Dinner date",
                isFinished = true
            )
        )
    )
}