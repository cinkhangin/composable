package com.naulian.composable.scc.grid_background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.White

fun Modifier.gridBackground(
    color: Color,
    lineColor: Color = Color(0xFFECECEC),
    lineThickness : Dp = 0.5.dp,
    spacing: Dp = 10.dp,
    shape: Shape = RectangleShape
) = background(color, shape).drawBehind {
    val spacingPx = spacing.toPx()
    val thicknessPx = lineThickness.toPx()
    val width = size.width
    val height = size.height

    val lineCountX = width / spacingPx
    val lineCountY = width / spacingPx

    val outline = shape.createOutline(size, layoutDirection, this)
    val path = when (outline) {
        is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
        is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
        is Outline.Generic -> outline.path
    }

    clipPath(path) {
        //draw vertical lines
        for (i in 1..lineCountX.toInt() ) {
            drawLine(
                color = lineColor,
                start = Offset(i * spacingPx, 0f),
                end = Offset(i * spacingPx, height),
                strokeWidth = thicknessPx
            )
        }

        //draw horizonal lines
        for (i in 1..lineCountY.toInt()) {
            drawLine(
                color = lineColor,
                start = Offset(0f, i * spacingPx),
                end = Offset(width, i * spacingPx),
                strokeWidth = thicknessPx
            )
        }
    }
}

@Preview
@Composable
private fun GridBackgroundPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .size(200.dp)
                .gridBackground(White, shape = RoundedCornerShape(10.dp))
        )
    }
}

val gridBackgroundCode by lazy {
    """
        fun Modifier.gridBackground(
            color: Color,
            lineColor: Color = Color(0xFFEFEFEF),
            spacing: Dp = 10.dp,
            shape: Shape = RectangleShape
        ) = background(color, shape).drawBehind {
            val spacingPx = spacing.toPx()
            val width = size.width
            val height = size.height

            val lineCountX = width / spacingPx
            val lineCountY = width / spacingPx

            val outline = shape.createOutline(size, layoutDirection, this)
            val path = when (outline) {
                is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
                is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
                is Outline.Generic -> outline.path
            }

            clipPath(path) {
                //draw vertical lines
                for (i in 1..lineCountX.toInt() - 1) {
                    drawLine(
                        color = lineColor,
                        start = Offset(i * spacingPx, 0f),
                        end = Offset(i * spacingPx, height),
                        strokeWidth = 1f
                    )
                }

                //draw horizonal lines
                for (i in 1..lineCountY.toInt() - 1) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, i * spacingPx),
                        end = Offset(width, i * spacingPx),
                        strokeWidth = 1f
                    )
                }
            }
        }
    """.trimIndent()
}
