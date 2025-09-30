package com.naulian.composable.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.LightGray
import com.naulian.neumorphic.ComposableTheme

@Composable
fun AnimatedCCAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    Box(modifier = modifier) {
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1f,
            animationSpec = InfiniteRepeatableSpec(
                animation = keyframes {
                    durationMillis = 2000
                    1f at 0
                    0.7f at 1000 using FastOutSlowInEasing
                    1f at 2000 using FastOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            ),
        )
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
                .rotate(rotation)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10)
                )
        )
    }
}

@Preview
@Composable
private fun AnimatedCCAnimationPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .background(LightGray)
                .padding(20.dp)
        ) {
            AnimatedCCAnimation(modifier = Modifier.size(200.dp))
        }
    }
}