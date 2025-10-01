package com.naulian.composable.scc.cornered_box

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.DarkGray
import com.naulian.modify.Gray

@Composable
fun CorneredBox(
    modifier: Modifier = Modifier,
    cornerColor: Color = Color.White,
    containerColor: Color = Color.Transparent,
    contentPadding : PaddingValues = PaddingValues(0.dp),
    cornerStrokeWidth: Dp = 6.dp,
    cornerSize: Dp = 20.dp,
    shape: Shape = RoundedCornerShape(10.dp),
    contentAlignment: Alignment = Alignment.TopStart,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(containerColor, shape)
            .clip(shape)
            .drawBehind {
                var cornerRadius = 0f
                val outline = shape.createOutline(size, layoutDirection, this)
                if (outline is Outline.Rounded) {
                    cornerRadius = outline.roundRect.topLeftCornerRadius.x
                }
                val centerX = size.width / 2f
                val centerY = size.height / 2f

                val startX = 0f
                val startY = 0f

                val endX = size.width
                val endY = size.height

                val horLineSize = size.width - cornerSize.toPx() * 2f
                val verLineSize = size.height - cornerSize.toPx() * 2f

                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)

                    drawRoundRect(
                        color = cornerColor,
                        cornerRadius = CornerRadius(cornerRadius),
                        style = Stroke(width = cornerStrokeWidth.toPx())
                    )

                    //top
                    drawLine(
                        color = Color.Yellow,
                        start = Offset(
                            x = centerX - horLineSize / 2,
                            y = startY
                        ),
                        end = Offset(
                            x = centerX + horLineSize / 2,
                            y = startY
                        ),
                        strokeWidth = 20f,
                        blendMode = BlendMode.Clear
                    )

                    //bottom
                    drawLine(
                        color =  Color.Yellow,
                        start = Offset(
                            x = centerX - horLineSize / 2,
                            y = endY
                        ),
                        end = Offset(
                            x = centerX + horLineSize / 2,
                            y = endY
                        ),
                        strokeWidth = 20f,
                        blendMode = BlendMode.Clear
                    )

                    //left
                    drawLine(
                        color =  Color.Yellow,
                        start = Offset(
                            x = startX,
                            y = centerY - verLineSize / 2
                        ),
                        end = Offset(
                            x = startX,
                            y = centerY + verLineSize / 2
                        ),
                        strokeWidth = 20f,
                        blendMode = BlendMode.Clear
                    )

                    //right
                    drawLine(
                        color =  Color.Yellow,
                        start = Offset(
                            x = endX,
                            y = centerY - verLineSize / 2
                        ),
                        end = Offset(
                            x = endX,
                            y = centerY + verLineSize / 2
                        ),
                        strokeWidth = 20f,
                        blendMode = BlendMode.Clear
                    )

                    restoreToCount(checkPoint)
                }
            }
            .clickable(onClick != null) { onClick?.invoke() }
            .padding(contentPadding),
        contentAlignment = contentAlignment,
        content = content
    )
}

@Preview
@Composable
private fun CorneredBoxPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF393939))
                .padding(20.dp)
        ) {
            CorneredBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                cornerColor = Gray,
                containerColor = DarkGray,
                onClick = {}
            ) {}
        }
    }
}

val corneredBoxCode by lazy {
    """
        @Composable
        fun CorneredBox(
            modifier: Modifier = Modifier,
            cornerColor: Color = Color.White,
            containerColor: Color = Color.Transparent,
            cornerStrokeWidth: Dp = 6.dp,
            cornerSize: Dp = 20.dp,
            shape: Shape = RoundedCornerShape(10.dp),
            contentAlignment: Alignment = Alignment.TopStart,
            onClick: (() -> Unit)? = null,
            content: @Composable BoxScope.() -> Unit
        ) {
            Box(
                modifier = modifier
                    .background(containerColor, shape)
                    .clip(shape)
                    .drawBehind {
                        var cornerRadius = 0f
                        val outline = shape.createOutline(size, layoutDirection, this)
                        if (outline is Outline.Rounded) {
                            cornerRadius = outline.roundRect.topLeftCornerRadius.x
                        }
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f
        
                        val startX = 0f
                        val startY = 0f
        
                        val endX = size.width
                        val endY = size.height
        
                        val horLineSize = size.width - cornerSize.toPx() * 2f
                        val verLineSize = size.height - cornerSize.toPx() * 2f
        
                        with(drawContext.canvas.nativeCanvas) {
                            val checkPoint = saveLayer(null, null)
        
                            drawRoundRect(
                                color = cornerColor,
                                cornerRadius = CornerRadius(cornerRadius),
                                style = Stroke(width = cornerStrokeWidth.toPx())
                            )
        
                            //top
                            drawLine(
                                color = Yellow,
                                start = Offset(
                                    x = centerX - horLineSize / 2,
                                    y = startY
                                ),
                                end = Offset(
                                    x = centerX + horLineSize / 2,
                                    y = startY
                                ),
                                strokeWidth = 20f,
                                blendMode = BlendMode.Clear
                            )
        
                            //bottom
                            drawLine(
                                color = Yellow,
                                start = Offset(
                                    x = centerX - horLineSize / 2,
                                    y = endY
                                ),
                                end = Offset(
                                    x = centerX + horLineSize / 2,
                                    y = endY
                                ),
                                strokeWidth = 20f,
                                blendMode = BlendMode.Clear
                            )
        
                            //left
                            drawLine(
                                color = Yellow,
                                start = Offset(
                                    x = startX,
                                    y = centerY - verLineSize / 2
                                ),
                                end = Offset(
                                    x = startX,
                                    y = centerY + verLineSize / 2
                                ),
                                strokeWidth = 20f,
                                blendMode = BlendMode.Clear
                            )
        
                            //right
                            drawLine(
                                color = Yellow,
                                start = Offset(
                                    x = endX,
                                    y = centerY - verLineSize / 2
                                ),
                                end = Offset(
                                    x = endX,
                                    y = centerY + verLineSize / 2
                                ),
                                strokeWidth = 20f,
                                blendMode = BlendMode.Clear
                            )
        
                            restoreToCount(checkPoint)
                        }
                    }
                    .clickable(onClick != null) { onClick?.invoke() },
                contentAlignment = contentAlignment,
                content = content
            )
        }
    """.trimIndent()
}