package com.teampatch.core.designsystem.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.teampatch.core.domain.fake.FakeTodos
import com.teampatch.core.domain.model.Todo

class TodoPreviewParameterProvider : PreviewParameterProvider<List<Todo>> {

    override val values: Sequence<List<Todo>> = sequenceOf(FakeTodos().get())
}