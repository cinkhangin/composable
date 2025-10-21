package com.naulian.composable.acc.radar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.White
import java.lang.Math.toDegrees
import kotlin.math.atan2

@Composable
fun RadarAnimation(
    modifier: Modifier = Modifier,
    sizeDp: Dp = 300.dp,
    radarColor: Color = Color(0xFF4CAF50)
) {
    Box(
        modifier = modifier
            .size(sizeDp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val infinite = rememberInfiniteTransition()
        val rotation by infinite.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        // Pulse scaling for circles
        val pulse by infinite.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )


        Canvas(modifier = Modifier
            .fillMaxSize()
        ) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val center = Offset(cx, cy)
            val maxRadius = size.minDimension / 2f * 0.9f * pulse

            // concentric rings
            for (i in 1..3) {
                drawCircle(
                    color = radarColor.copy(alpha = 0.5f / i),
                    radius = maxRadius * (i / 3f),
                    center = center,
                    style = Stroke(width = 10f)
                )
            }

            //sweep
            rotate(degrees = rotation, pivot = center) {
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(
                        radarColor.copy(alpha = 0.8f),
                        radarColor.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    start = center,
                    end = Offset(center.x + maxRadius, center.y)
                )

                drawLine(
                    brush = gradientBrush,
                    start = center,
                    end = Offset(center.x + maxRadius, center.y),
                    strokeWidth = 6f
                )
            }

            // fixed dots that sync fade with sweep line angle
            val dotPositions = listOf(
                Offset(cx + maxRadius * 0.5f, cy - maxRadius * 0.3f),
                Offset(cx - maxRadius * 0.7f, cy + maxRadius * 0.2f),
                Offset(cx + maxRadius * 0.2f, cy + maxRadius * 0.7f)
            )

            dotPositions.forEach { offset ->
                val dx = offset.x - cx
                val dy = offset.y - cy
                var angle = toDegrees(atan2(dy, dx).toDouble()).toFloat()
                if (angle < 0) angle += 360f


                val diff = kotlin.math.abs(rotation - angle).let { if (it > 180) 360 - it else it }
                val alpha = (1f - (diff / 60f)).coerceIn(0f, 1f)

                drawCircle(
                    color = radarColor.copy(alpha = alpha),
                    radius = 10f,
                    center = offset
                )
            }
        }

        // Center avatar
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.White)
                .drawBehind {
                    drawCircle(
                        color = Color.Black.copy(alpha = 0.08f),
                        radius = size.minDimension / 2f,
                        style = Stroke(width = 2.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(com.naulian.composable.core.R.drawable.a), contentDescription = "Avatar", contentScale = ContentScale.FillBounds,
                modifier = Modifier.background(color = Color.Transparent,CircleShape))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RadarAnimationPreview() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RadarAnimation(sizeDp = 320.dp)
    }
}


@Preview
@Composable
private fun RadarPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(White)
        ) {
            RadarAnimation()
        }
    }
}