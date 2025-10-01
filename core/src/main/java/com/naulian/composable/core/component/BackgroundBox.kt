package com.naulian.composable.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val defaultShape = RoundedCornerShape(10)

val defaultContainerColor @Composable get() = MaterialTheme.colorScheme.primary.copy(0.3f)
val defaultSurfaceColor = Color.White.copy(0.4f)

@Composable
fun BackgroundBox(
    modifier: Modifier = Modifier,
    color: Color = defaultContainerColor,
    shape: Shape = defaultShape,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = color, shape = shape)
            .padding(12.dp),
        contentAlignment = contentAlignment,
        content = content
    )
}