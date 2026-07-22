package com.ckgin.composable.component.acc

import androidx.compose.foundation.shape.GenericShape
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val HeartShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height
    moveTo(width / 2f, height * 0.92f)
    cubicTo(width * 0.08f, height * 0.62f, 0f, height * 0.34f, width * 0.2f, height * 0.18f)
    cubicTo(width * 0.34f, height * 0.06f, width * 0.48f, height * 0.16f, width / 2f, height * 0.3f)
    cubicTo(width * 0.52f, height * 0.16f, width * 0.66f, height * 0.06f, width * 0.8f, height * 0.18f)
    cubicTo(width, height * 0.34f, width * 0.92f, height * 0.62f, width / 2f, height * 0.92f)
    close()
}

val FlowerShape = GenericShape { size, _ ->
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val scale = size.minDimension
    repeat(65) { index ->
        val angle = index / 64f * 2f * PI
        val radius = scale * (0.36f + 0.12f * cos(angle * 8f)).toFloat()
        val x = centerX + radius * cos(angle).toFloat()
        val y = centerY + radius * sin(angle).toFloat()
        if (index == 0) moveTo(x, y) else lineTo(x, y)
    }
    close()
}
