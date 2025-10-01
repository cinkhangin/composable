package com.naulian.composable.acc

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.theme.ComposablePreview

private val defaultShape = RoundedCornerShape(10)

private val defaultBackground @Composable get() = MaterialTheme.colorScheme.primary.copy(0.2f)
private val defaultSurface @Composable get() = MaterialTheme.colorScheme.surface.copy(0.3f)


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