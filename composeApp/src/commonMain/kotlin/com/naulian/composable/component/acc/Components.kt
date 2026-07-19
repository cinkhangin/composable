package com.naulian.composable.component.acc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.component.defaultContainerColor
import com.naulian.composable.core.component.defaultSurfaceColor
import com.naulian.composable.core.theme.ComposablePreview

@Composable
fun EmptyComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {

    }
}

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

@Composable
private fun TypingTextComponentPreview() {
    ComposablePreview {
        TypingTextComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun PulseComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        PulseAnimation(
            modifier = Modifier.fillMaxSize(0.7f),
            shape = FlowerShape,
            color = defaultSurfaceColor.copy(0.5f)
        )
    }
}

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

@Composable
private fun RadarEffectComponentPreview() {
    ComposablePreview {
        RadarEffectComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun VinylComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier) {
        VinylDiskRotating(
            Modifier.fillMaxSize(),
            diskColor = defaultSurfaceColor.copy(alpha = .85f),
            needleColors = listOf(
                defaultContainerColor.copy(alpha = .3f),
                defaultContainerColor.copy(alpha = .6f),
                defaultContainerColor.copy(alpha = .9f)
            )
        )
    }
}

@Composable
private fun VinylComponentPreview() {
    ComposablePreview {
        VinylComponent(
            modifier = Modifier
                .size(120.dp),
        )
    }
}
