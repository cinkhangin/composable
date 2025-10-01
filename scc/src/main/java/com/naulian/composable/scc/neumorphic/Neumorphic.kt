package com.naulian.composable.scc.neumorphic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import org.intellij.lang.annotations.Language

@Composable
fun Modifier.neumorphicUp(
    shape: Shape,
    shadowPadding: Dp,
    color: Color = MaterialTheme.colorScheme.background
) = background(color = color, shape = shape)
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceBright,
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceDim,
            offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
        )
    )

@Composable
fun Modifier.neumorphicDown(
    shape: Shape,
    shadowPadding: Dp,
    color: Color = MaterialTheme.colorScheme.background
) = background(color = color, shape = shape)
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceBright,
            offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
        )
    )
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceDim,
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )


@Composable
fun NeuMorphicUP(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    bumpElevation: Dp = 6.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicUp(
            shape = shape,
            shadowPadding = bumpElevation,
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}


@Composable
fun NeuMorphicDown(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicDown(
            shape = shape,
            shadowPadding = contentPadding,
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Preview
@Composable
private fun NeumorphicBoxPreview() {
    ComposableTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            NeuMorphicUP(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                contentAlignment = Alignment.Center
            )
        }
    }
}

@Suppress("UnusedReceiverParameter", "FunctionName")
@Language("kotlin")
val neumorphicCode = """
@Composable
fun Modifier.neumorphicUp(
    shape: Shape,
    shadowPadding: Dp,
    color: Color = MaterialTheme.colorScheme.background
) = background(color = color, shape = shape)
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceBright,
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceDim,
            offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
        )
    )


@Composable
fun Modifier.neumorphicDown(
    shape: Shape,
    shadowPadding: Dp,
    color: Color = MaterialTheme.colorScheme.background
) = background(color = color, shape = shape)
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceBright,
            offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
        )
    )
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = MaterialTheme.colorScheme.surfaceDim,
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )


@Composable
fun NeuMorphicUP(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    bumpElevation: Dp = 6.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicUp(
            shape = shape,
            shadowPadding = bumpElevation,
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}


@Composable
fun NeuMorphicDown(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicDown(
            shape = shape,
            shadowPadding = contentPadding,
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}
""".trimIndent()