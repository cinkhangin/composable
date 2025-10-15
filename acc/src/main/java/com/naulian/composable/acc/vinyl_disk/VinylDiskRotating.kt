package com.naulian.composable.acc.vinyl_disk

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun VinylDiskRotating(
    modifier: Modifier = Modifier,
    diskColor: Color = Color.Black,
    needleColors: List<Color> = emptyList()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "disk")
    val diskAngle by infiniteTransition.animateFloat(
        0f,
        360f,
        infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val lightingAngle by infiniteTransition.animateFloat(
        360f,
        0f,
        infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    VinylDisk(
        modifier,
        diskColor = diskColor,
        needleColors = needleColors,
        diskAngle = diskAngle,
        lightingAngle = lightingAngle
    )
}