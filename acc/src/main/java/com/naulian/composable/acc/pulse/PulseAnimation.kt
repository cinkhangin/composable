package com.naulian.composable.acc.pulse

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.White
import com.naulian.neumorphic.ComposableTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialShapes.Heart.toShape()
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 800
                1.0f at 0 using LinearEasing
                1.2f at 400 using LinearEasing
                1.0f at 800 using LinearEasing
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .scale(pulse)
            .background(
                shape = shape,
                color = color,
            )
    )
}

@Preview
@Composable
private fun PulsingHeartPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(White)
        ) {
            PulseAnimation(modifier = Modifier.size(100.dp))
        }
    }
}