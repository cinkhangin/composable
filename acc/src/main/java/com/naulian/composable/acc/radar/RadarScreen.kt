package com.naulian.composable.acc.radar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.columnItem

@Composable
fun RadarScreen() {
    val navController = LocalNavController.current

    RadarScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RadarScreenUI(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Radar Effect",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem {
                RadarAnimation(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(20.dp))

                CodeBlock(
                    source = RadarCode,
                    codeName = "Radar Effect",
                    language = "kotlin"
                )
            }
        }
    }
}

val RadarCode = """
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
                    """.trimIndent()