package com.naulian.composable.acc

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.theme.ComposablePreview

@Composable
fun EmptyComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {

    }
}

@Preview
@Composable
private fun GlassCardComponentPreview() {
    ComposablePreview {
        EmptyComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}