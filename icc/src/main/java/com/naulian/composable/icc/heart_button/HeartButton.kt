package com.naulian.composable.icc.heart_button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.icc.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun HeartButton(
    modifier: Modifier = Modifier,
    isInitiallyLiked: Boolean = false,
    burstCount: Int = 6,
    burstDuration: Int = 800,
    particleColors: List<Color> = listOf(Color.Red),
    activeColor: Color = Color.Red,
    defaultColor: Color = MaterialTheme.colorScheme.primary,
    onLikeChanged: (Boolean) -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(isInitiallyLiked) }
    var triggerBurst by remember { mutableStateOf(false) }

    // Main heart scale animation
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        finishedListener = { if (isLiked) triggerBurst = true }
    )

    // Burst heart state
    var burstHeart by remember { mutableStateOf(emptyList<BurstHeart>()) }

    LaunchedEffect(triggerBurst) {
        if (triggerBurst) {
            burstHeart = List(burstCount) {
                BurstHeart(
                    angle = Random.nextFloat() * 360f,
                    distance = Random.nextInt(40, 120).toFloat()
                )
            }
            triggerBurst = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        burstHeart.forEach { heart ->
            val alpha = remember { Animatable(1f) }
            val progress = remember { Animatable(0f) }
            LaunchedEffect(heart) {
                launch {
                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(burstDuration, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    alpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(burstDuration, easing = LinearEasing)
                    )
                }
                launch {
                    delay(burstDuration.toLong())
                    burstHeart = burstHeart - heart
                }
            }

            val x =
                heart.distance * progress.value * cos(Math.toRadians(heart.angle.toDouble()))
            val y =
                heart.distance * progress.value * sin(Math.toRadians(heart.angle.toDouble()))

            Icon(
                painter = painterResource(id = R.drawable.heart_filled),
                contentDescription = "Burst Heart",
                tint = particleColors.random(),
                modifier = Modifier
                    .offset { IntOffset(x.toInt(), y.toInt()) }
                    .alpha(alpha.value)
                    .size(20.dp)
            )
        }

        // Main heart
        Icon(
            painter = painterResource(id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_unfilled),
            contentDescription = "heart",
            tint = if (isLiked) activeColor else defaultColor,
            modifier = Modifier
                .scale(scale)
                .padding(8.dp)
                .size(48.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    isLiked = !isLiked
                    onLikeChanged(isLiked)

                    // Trigger burst every time you like
                    if (isLiked) {
                        triggerBurst = true
                    }
                }
        )
    }
}

data class BurstHeart(
    val angle: Float,
    val distance: Float
)


@Suppress("FunctionName")
@Language("kotlin")
val heartButtonCode = """     
@Composable
fun HeartButton(
    modifier: Modifier = Modifier,
    isInitiallyLiked: Boolean = false,
    burstCount: Int = 6,
    burstDuration: Int = 800,
    onLikeChanged: (Boolean) -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(isInitiallyLiked) }
    var triggerBurst by remember { mutableStateOf(false) }

    // Main heart scale animation
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        finishedListener = { if (isLiked) triggerBurst = true }
    )

    // Burst heart state
    var burstHeart by remember { mutableStateOf(emptyList<BurstHeart>()) }

    if (triggerBurst) {
        LaunchedEffect(triggerBurst) {
            burstHeart = List(burstCount) {
                BurstHeart(
                    angle = Random.nextFloat() * 360f,
                    distance = Random.nextInt(40, 120).toFloat()
                )
            }
            triggerBurst = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        burstHeart.forEach { heart ->
            val alpha = remember { Animatable(1f) }
            val progress = remember { Animatable(0f) }
            LaunchedEffect(heart) {
                launch {
                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(burstDuration, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    alpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(burstDuration, easing = LinearEasing)
                    )
                }
                launch {
                    delay(burstDuration.toLong())
                    burstHeart = burstHeart - heart
                }
            }

            val x =
                heart.distance * progress.value * cos(Math.toRadians(heart.angle.toDouble()))
            val y =
                heart.distance * progress.value * sin(Math.toRadians(heart.angle.toDouble()))

            Icon(
                painter = painterResource(id = R.drawable.heart_filled),
                contentDescription = "Burst Heart",
                tint = Color.Red,
                modifier = Modifier
                    .offset { IntOffset(x.toInt(), y.toInt()) }
                    .alpha(alpha.value)
                    .size(20.dp)
            )
        }

        // Main heart
        Icon(
            painter = painterResource(id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_unfilled),
            contentDescription = "heart",
            tint = if (isLiked) Color.Red else Color.Gray,
            modifier = Modifier
                .scale(scale)
                .padding(8.dp)
                .size(48.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    isLiked = !isLiked
                    onLikeChanged(isLiked)

                    // Trigger burst every time you like
                    if (isLiked) {
                        triggerBurst = true
                    }
                }
        )
    }
}

private data class BurstHeart(
    val angle: Float,
    val distance: Float
)
""".trimIndent()