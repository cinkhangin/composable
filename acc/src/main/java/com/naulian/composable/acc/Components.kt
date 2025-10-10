package com.naulian.composable.acc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.acc.bubbles.BubbleRise
import com.naulian.composable.acc.clock.Clock
import com.naulian.composable.acc.glitch.GlitchText
import com.naulian.composable.acc.pulse.PulseAnimation
import com.naulian.composable.acc.radar.RadarAnimation
import com.naulian.composable.acc.typing.TypingText
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.component.defaultSurfaceColor
import com.naulian.composable.core.theme.ComposablePreview

@Composable
fun EmptyComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {

    }
}

@Preview
@Composable
private fun EmptyComponentPreview() {
    ComposablePreview {
        EmptyComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun TypingTextComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        TypingText(
            text = "Hello", fontSize = 24.sp, delay = 520,
            color = defaultSurfaceColor.copy(0.9f)
        )
    }
}

@Preview
@Composable
private fun TypingTextComponentPreview() {
    ComposablePreview {
        TypingTextComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PulseComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        PulseAnimation(
            modifier = Modifier.fillMaxSize(0.7f),
            shape = MaterialShapes.Flower.toShape(),
            color = defaultSurfaceColor.copy(0.5f)
        )
    }
}

@Preview
@Composable
private fun PulseComponentPreview() {
    ComposablePreview {
        PulseComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun GlitchEffectComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        GlitchText(
            text = "GLITCH",
            glitchColors = defaultSurfaceColor.copy(0.3f) to defaultSurfaceColor.copy(0.5f),
            color = defaultSurfaceColor.copy(0.7f)
        )
    }
}

@Preview
@Composable
private fun GlitchEffectComponentPreview() {
    ComposablePreview {
        GlitchEffectComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun ClockComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        Clock(
            hourColor = defaultSurfaceColor.copy(0.9f),
            minuteColor = defaultSurfaceColor.copy(0.7f),
            secondColor = defaultSurfaceColor,
            hourHandThickness = 4.dp,
            minuteHandThickness = 2.dp,
            secondHandThickness = 1.dp
        )
    }
}

@Preview
@Composable
private fun ClockComponentPreview() {
    ComposablePreview {
        ClockComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun BubblesComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        BubbleRise(
            modifier = Modifier.fillMaxSize(),
            bubbleCount = 5,
            color = defaultSurfaceColor.copy(0.4f)
        )
    }
}

@Preview
@Composable
private fun BubblesComponentPreview() {
    ComposablePreview {
        BubblesComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun RadarEffectComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        RadarAnimation()
    }
}

@Preview
@Composable
private fun RadarEffectComponentPreview() {
    ComposablePreview {
        RadarEffectComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}