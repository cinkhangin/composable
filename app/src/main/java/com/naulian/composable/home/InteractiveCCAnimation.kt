package com.naulian.composable.home

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.R
import com.naulian.modify.LightGray
import com.naulian.neumorphic.ComposableTheme

@Composable
fun InteractiveCCAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    var center by remember { mutableStateOf(IntOffset.Zero) }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            val size = coordinates.size
            center = IntOffset(size.width / 2, size.height / 2)
        }
    ) {

        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1f,
            animationSpec = InfiniteRepeatableSpec(
                animation = keyframes {
                    durationMillis = 2000
                    1f at 500
                    0.8f at 750 using FastOutSlowInEasing
                    1f at 1000 using FastOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10)
                )
        )

        val offset by infiniteTransition.animateValue(
            initialValue = IntOffset(center.y, center.y * 3),
            targetValue = IntOffset(center.y, center.y * 3),
            animationSpec = InfiniteRepeatableSpec(
                animation = keyframes {
                    durationMillis = 2000
                    IntOffset(center.y, center.y * 3) at 0 using FastOutSlowInEasing
                    center at 500 using FastOutSlowInEasing
                    center at 1500 using FastOutSlowInEasing
                    IntOffset(center.y, center.y * 3) at 2000 using FastOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            ),
            typeConverter = TwoWayConverter(
                convertToVector = { AnimationVector2D(it.x.toFloat(), it.y.toFloat()) },
                convertFromVector = { IntOffset(it.v1.toInt(), it.v2.toInt()) }
            ),
        )

        Icon(
            modifier = Modifier.offset { offset }
                .scale(scale),
            painter = painterResource(R.drawable.ic_cursor),
            contentDescription = "Cursor Icon",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun InteractiveCCAnimationPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .background(LightGray)
                .padding(20.dp)
        ) {
            InteractiveCCAnimation(modifier = Modifier.size(200.dp))
        }
    }
}