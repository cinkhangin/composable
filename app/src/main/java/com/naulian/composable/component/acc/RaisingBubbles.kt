package com.naulian.composable.acc.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.intellij.lang.annotations.Language
import kotlin.random.Random

@Composable
fun BubbleRise(
    modifier: Modifier = Modifier,
    bubbleCount: Int = 12,
    color : Color = MaterialTheme.colorScheme.primary
) {
    val bubbles = remember {
        List(bubbleCount) {
            listOf(
                Random.nextFloat(),
                Random.nextInt(8, 24).toFloat(),
                Random.nextInt(5000, 9000).toFloat(),
                Random.nextLong(0, 4000).toFloat()
            )
        }
    }

    Box(modifier = modifier) {
        bubbles.forEach { bubble ->
            RisingBubble(
                x = bubble[0],
                radius = bubble[1],
                durationMillis = bubble[2].toInt(),
                delayMillis = bubble[3].toInt(),
                color = color
            )
        }
    }
}

@Composable
fun RisingBubble(
    x: Float,
    radius: Float,
    durationMillis: Int,
    delayMillis: Int,
    color : Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bubbleRise")

    val yAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "yAnim"
    )

    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "alphaAnim"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val xPos = this.size.width * x
        val yPos = this.size.height * yAnim
        drawCircle(
            color = color.copy(alpha = alphaAnim),
            radius = radius,
            center = Offset(xPos, yPos)
        )
    }
}

@Language("kotlin")
val bubbleRiseCode = """
@Composable
fun BubbleRise(
    modifier: Modifier = Modifier,
    bubbleCount: Int = 12
) {
    val bubbles = remember {
        List(bubbleCount) {
            listOf(
                Random.nextFloat(),                     
                Random.nextInt(8, 24).toFloat(),      
                Random.nextInt(5000, 9000).toFloat(),    
                Random.nextLong(0, 4000).toFloat()       
            )
        }
    }

    Box(modifier = modifier) {
        bubbles.forEach { bubble ->
            RisingBubble(
                x = bubble[0],
                radius = bubble[1],
                durationMillis = bubble[2].toInt(),
                delayMillis = bubble[3].toInt()
            )
        }
    }
}

@Composable
fun RisingBubble(
    x: Float,
    radius: Float,
    durationMillis: Int,
    delayMillis: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bubbleRise")

    val yAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "yAnim"
    )

    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "alphaAnim"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val xPos = this.size.width * x
        val yPos = this.size.height * yAnim
        drawCircle(
            color = Color(0xFF9C27B0).copy(alpha = alphaAnim),
            radius = radius,
            center = Offset(xPos, yPos)
        )
    }
}
""".trimIndent()
