package com.naulian.composable.acc.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Clock(
    modifier: Modifier = Modifier,
    hourColor: Color = MaterialTheme.colorScheme.primary,
    minuteColor: Color = MaterialTheme.colorScheme.onBackground,
    secondColor: Color = MaterialTheme.colorScheme.onBackground,
    hourHandThickness: Dp = 8.dp,
    minuteHandThickness: Dp = 6.dp,
    secondHandThickness: Dp = 4.dp
) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            delay(990L) // Update every second
        }
    }

    val hours = currentTime.get(Calendar.HOUR_OF_DAY)
    val minutes = currentTime.get(Calendar.MINUTE)
    val seconds = currentTime.get(Calendar.SECOND)

    val hourAngle = (hours % 12 + minutes / 60f) * 30f
    val minuteAngle = minutes * 6f
    val secondAngle = seconds * 6f

    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Draw hour markers
            for (i in 0 until 12) {
                val angle = i * 30f // 360 / 12 = 30
                rotate(angle, center) {
                    drawLine(
                        color = hourColor.copy(alpha = 0.6f),
                        start = Offset(center.x, center.y - radius * 0.9f),
                        end = Offset(center.x, center.y - radius * 0.8f),
                        strokeWidth = hourHandThickness.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            // Draw minute markers
            for (i in 0 until 60) {
                if (i % 5 != 0) { // Don't draw over hour markers
                    val angle = i * 6f // 360 / 60 = 6
                    rotate(angle, center) {
                        drawLine(
                            color = minuteColor.copy(alpha = 0.4f),
                            start = Offset(center.x, center.y - radius * 0.9f),
                            end = Offset(center.x, center.y - radius * 0.85f),
                            strokeWidth = minuteHandThickness.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            // Draw hour hand
            drawHand(center, hourAngle, radius * 0.5f, hourColor, hourHandThickness.toPx())
            // Draw minute hand
            drawHand(center, minuteAngle, radius * 0.65f, minuteColor, minuteHandThickness.toPx())
            // Draw second hand
            drawHand(center, secondAngle, radius * 0.8f, secondColor, secondHandThickness.toPx())
            // Center dot
            drawCircle(color = secondColor, radius = hourHandThickness.toPx(), center = center)
        }
    }
}

private fun DrawScope.drawHand(
    center: Offset,
    angle: Float,
    length: Float,
    color: Color,
    strokeWidth: Float
) {
    val angleRad = (angle - 90) * (PI / 180f).toFloat() // Subtract 90 to start from 12 o'clock
    val endX = center.x + length * cos(angleRad)
    val endY = center.y + length * sin(angleRad)
    drawLine(
        color = color,
        start = center,
        end = Offset(endX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

@Preview
@Composable
private fun ClockPreview() {
    ComposableTheme {
        Clock()
    }
}

val clockCode = """
@Composable
fun Clock(
    modifier: Modifier = Modifier,
    hourColor: Color = MaterialTheme.colorScheme.primary,
    minuteColor: Color = MaterialTheme.colorScheme.onBackground,
    secondColor : Color = MaterialTheme.colorScheme.onBackground,
) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            delay(990L) // Update every second
        }
    }

    val hours = currentTime.get(Calendar.HOUR_OF_DAY)
    val minutes = currentTime.get(Calendar.MINUTE)
    val seconds = currentTime.get(Calendar.SECOND)

    val hourAngle = (hours % 12 + minutes / 60f) * 30f
    val minuteAngle = minutes * 6f
    val secondAngle = seconds * 6f

    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            // Draw hour markers
            for (i in 0 until 12) {
                val angle = i * 30f // 360 / 12 = 30
                rotate(angle, center) {
                    drawLine(
                        color = hourColor.copy(alpha = 0.6f),
                        start = Offset(center.x, center.y - radius * 0.9f),
                        end = Offset(center.x, center.y - radius * 0.8f),
                        strokeWidth = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            // Draw minute markers
            for (i in 0 until 60) {
                if (i % 5 != 0) { // Don't draw over hour markers
                    val angle = i * 6f // 360 / 60 = 6
                    rotate(angle, center) {
                        drawLine(
                            color = minuteColor.copy(alpha = 0.4f),
                            start = Offset(center.x, center.y - radius * 0.9f),
                            end = Offset(center.x, center.y - radius * 0.85f),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }
            }

            // Draw hour hand
            drawHand(center, hourAngle, radius * 0.5f, hourColor, 8.dp.toPx())
            // Draw minute hand
            drawHand(center, minuteAngle, radius * 0.65f, minuteColor, 6.dp.toPx())
            // Draw second hand
            drawHand(center, secondAngle, radius * 0.8f, secondColor, 4.dp.toPx())
            // Center dot
            drawCircle(color = secondColor, radius = 8.dp.toPx(), center = center)
        }
    }
}

private fun DrawScope.drawHand(
    center: Offset,
    angle: Float,
    length: Float,
    color: Color,
    strokeWidth: Float
) {
    val angleRad = (angle - 90) * (PI / 180f).toFloat() // Subtract 90 to start from 12 o'clock
    val endX = center.x + length * cos(angleRad)
    val endY = center.y + length * sin(angleRad)
    drawLine(
        color = color,
        start = center,
        end = Offset(endX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}
""".trimIndent()