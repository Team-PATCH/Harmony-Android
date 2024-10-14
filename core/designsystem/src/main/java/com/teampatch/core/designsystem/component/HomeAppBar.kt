package com.teampatch.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teampatch.core.designsystem.R
import com.teampatch.core.designsystem.theme.HarmonyTheme

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .then(modifier)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_harmony_appbar),
            contentDescription = "appbar_logo",
            modifier = Modifier.padding(start = 28.dp)
        )
        actions()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeAppBarPreview() {
    HarmonyTheme {
        HomeAppBar {
            Image(
                painter = painterResource(R.drawable.ic_my_appbar),
                contentDescription = "my",
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}