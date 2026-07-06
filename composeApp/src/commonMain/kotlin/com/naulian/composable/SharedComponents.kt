package com.naulian.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GlitchText(
    modifier: Modifier = Modifier,
    text: String = "GLITCH EFFECT",
    glitchColors: Pair<Color, Color> = Color.Red to Color.Blue,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glitch")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(200, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "glitchOffset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = glitchColors.first,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(
                x = (-2 + offset * 4).dp,
                y = (-2 + offset * 4).dp
            )
        )
        Text(
            text = text,
            color = glitchColors.second,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(
                x = (2 - offset * 4).dp,
                y = (2 - offset * 4).dp
            )
        )
        Text(
            text = text,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TypingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    delayMillis: Long = 80,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {
    var textIndex by remember(text) { mutableIntStateOf(0) }

    LaunchedEffect(text, delayMillis) {
        while (true) {
            textIndex++
            delay(delayMillis)
        }
    }

    Text(
        modifier = modifier,
        text = text.take(textIndex % (text.length + 1)),
        fontStyle = fontStyle,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
fun BubbleRise(
    modifier: Modifier = Modifier,
    bubbleCount: Int = 12,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val bubbles = remember(bubbleCount) {
        List(bubbleCount) {
            Bubble(
                x = Random.nextFloat(),
                radius = Random.nextInt(8, 24).toFloat(),
                durationMillis = Random.nextInt(5000, 9000),
                delayMillis = Random.nextLong(0, 4000).toInt()
            )
        }
    }

    Box(modifier = modifier) {
        bubbles.forEach { bubble ->
            RisingBubble(
                bubble = bubble,
                color = color
            )
        }
    }
}

private data class Bubble(
    val x: Float,
    val radius: Float,
    val durationMillis: Int,
    val delayMillis: Int
)

@Composable
private fun RisingBubble(
    bubble: Bubble,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bubbleRise")
    val yAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = bubble.durationMillis,
                easing = LinearEasing,
                delayMillis = bubble.delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleY"
    )
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = bubble.durationMillis,
                easing = LinearEasing,
                delayMillis = bubble.delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "bubbleAlpha"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = color.copy(alpha = alphaAnim),
            radius = bubble.radius,
            center = Offset(
                x = size.width * bubble.x,
                y = size.height * yAnim
            )
        )
    }
}

fun Modifier.gridBackground(
    color: Color,
    lineColor: Color = Color(0xFFECECEC),
    lineThickness: Dp = 0.5.dp,
    spacing: Dp = 10.dp,
    shape: Shape = RectangleShape
) = background(color, shape).drawBehind {
    val spacingPx = spacing.toPx()
    val thicknessPx = lineThickness.toPx()
    val width = size.width
    val height = size.height
    val lineCountX = width / spacingPx
    val lineCountY = height / spacingPx
    val outline = shape.createOutline(size, layoutDirection, this)
    val path = when (outline) {
        is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
        is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
        is Outline.Generic -> outline.path
    }

    clipPath(path) {
        for (i in 1..lineCountX.toInt()) {
            drawLine(
                color = lineColor,
                start = Offset(i * spacingPx, 0f),
                end = Offset(i * spacingPx, height),
                strokeWidth = thicknessPx
            )
        }
        for (i in 1..lineCountY.toInt()) {
            drawLine(
                color = lineColor,
                start = Offset(0f, i * spacingPx),
                end = Offset(width, i * spacingPx),
                strokeWidth = thicknessPx
            )
        }
    }
}

@Composable
fun RaisedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    height: Dp = 48.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    colorDark: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f),
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    content: @Composable RowScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val animatedDepth by animateDpAsState(
        targetValue = if (isPressed) elevation / 3 else elevation,
        animationSpec = tween(durationMillis = 150),
        label = "buttonDepth",
        finishedListener = { isPressed = false }
    )
    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (elevation * 2 / 3) else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset",
        finishedListener = { isPressed = false }
    )

    Box(
        modifier = modifier.height(height + elevation),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .fillMaxWidth()
                .height(height + animatedDepth)
                .background(color = colorDark, shape = shape)
        )
        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = MaterialTheme.typography.labelLarge
        ) {
            Row(
                modifier = Modifier
                    .offset(y = animatedButtonOffset)
                    .fillMaxWidth()
                    .height(height)
                    .background(color = color, shape = shape)
                    .clip(shape)
                    .clickable {
                        isPressed = true
                        onClick()
                    },
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
private fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides LocalTextStyle.current.merge(textStyle),
        content = content
    )
}

@Composable
fun PhysicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "PhysicsButtonScale"
    )

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        modifier = modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale
        ),
        interactionSource = remember { MutableInteractionSource() },
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text)
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(150)
            pressed = false
        }
    }
}

@Composable
fun GlassDemo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF155E63), Color(0xFF1F2937))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedParticles(particleCount = 18)
        GlassCard(
            title = "Shared Card",
            value = "KMP + CMP",
            width = 220.dp,
            height = 140.dp
        )
    }
}

@Composable
fun GlassCard(
    title: String? = null,
    value: String? = null,
    width: Dp,
    height: Dp,
    content: @Composable (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glass")
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glassFloat"
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .graphicsLayer { translationY = floatingOffset }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .liquidGlassEffect(
                    shape = RoundedCornerShape(28.dp),
                    borderColor = Color.White.copy(alpha = 0.4f),
                    intensity = 0.9f
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (content != null) {
                content()
            } else {
                if (title != null) {
                    Text(title, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                }
                if (value != null) {
                    Text(
                        value,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun Modifier.liquidGlassEffect(
    shape: Shape,
    borderColor: Color,
    intensity: Float = 0.7f
): Modifier {
    return clip(shape)
        .background(
            Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.15f * intensity),
                    Color.White.copy(alpha = 0.05f * intensity),
                    Color.Transparent
                ),
                center = Offset(0.3f, 0.3f),
                radius = 500f
            )
        )
        .drawBehind {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f * intensity),
                        Color.Transparent
                    ),
                    center = Offset(0.3f, 0.3f),
                    radius = size.width * 0.8f
                ),
                radius = size.width * 0.7f,
                center = Offset(
                    size.width * 0.3f,
                    size.height * 0.3f
                )
            )
        }
        .border(BorderStroke(1.dp, borderColor), shape)
}

@Composable
private fun AnimatedParticles(particleCount: Int) {
    val particles = remember { List(particleCount) { Particle() } }

    Box(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            var position by remember { mutableStateOf(particle.reset()) }

            LaunchedEffect(particle) {
                while (true) {
                    position = particle.update()
                    delay(16)
                }
            }

            Box(
                modifier = Modifier
                    .offset(x = position.x.dp, y = position.y.dp)
                    .size(particle.size.dp)
                    .graphicsLayer {
                        alpha = particle.opacity
                        rotationZ = particle.rotation
                    }
                    .background(
                        color = Color.White.copy(alpha = particle.opacity * 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}

private data class Particle(
    var x: Float = 0f,
    var y: Float = 0f,
    var size: Float = 0f,
    var speed: Float = 0f,
    var direction: Float = 0f,
    var opacity: Float = 0f,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f
) {
    fun reset(): Offset {
        x = (0..1000).random().toFloat()
        y = (0..2000).random().toFloat()
        size = (2..8).random().toFloat()
        speed = Random.nextFloat() * 1.3f + 0.2f
        direction = (0..360).random().toFloat()
        opacity = Random.nextFloat() * 0.3f + 0.1f
        rotation = (0..360).random().toFloat()
        rotationSpeed = Random.nextFloat() * 4f - 2f
        return Offset(x, y)
    }

    fun update(): Offset {
        val radians = direction / 180f * PI.toFloat()
        x += cos(radians) * speed
        y += sin(radians) * speed
        rotation += rotationSpeed

        if (x < -100f || x > 1100f || y < -100f || y > 2100f) {
            reset()
        }

        return Offset(x, y)
        }
}

@Composable
fun PulseAnimation(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
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
        ),
        label = "pulseScale"
    )

    Box(
        modifier = modifier
            .scale(pulse)
            .background(color = color, shape = shape)
    )
}

@Composable
fun RadarAnimation(
    modifier: Modifier = Modifier,
    radarColor: Color = Color(0xFF4CAF50)
) {
    Box(modifier = modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
        val infinite = rememberInfiniteTransition(label = "radar")
        val rotation by infinite.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "radarRotation"
        )
        val pulse by infinite.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "radarPulse"
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = size.minDimension / 2f * 0.9f * pulse

            for (i in 1..3) {
                drawCircle(
                    color = radarColor.copy(alpha = 0.5f / i),
                    radius = maxRadius * (i / 3f),
                    center = center,
                    style = Stroke(width = 8f)
                )
            }

            rotate(degrees = rotation, pivot = center) {
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            radarColor.copy(alpha = 0.8f),
                            radarColor.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        start = center,
                        end = Offset(center.x + maxRadius, center.y)
                    ),
                    start = center,
                    end = Offset(center.x + maxRadius, center.y),
                    strokeWidth = 6f
                )
            }

            listOf(
                Offset(center.x + maxRadius * 0.5f, center.y - maxRadius * 0.3f),
                Offset(center.x - maxRadius * 0.7f, center.y + maxRadius * 0.2f),
                Offset(center.x + maxRadius * 0.2f, center.y + maxRadius * 0.7f)
            ).forEach { dot ->
                var angle = kotlin.math.atan2(dot.y - center.y, dot.x - center.x) * 180f / PI.toFloat()
                if (angle < 0) angle += 360f
                val diff = abs(rotation - angle).let { if (it > 180) 360 - it else it }
                drawCircle(
                    color = radarColor.copy(alpha = (1f - (diff / 60f)).coerceIn(0f, 1f)),
                    radius = 10f,
                    center = dot
                )
            }
        }

        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text("CMP", color = radarColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    hourColor: Color = MaterialTheme.colorScheme.primary,
    minuteColor: Color = MaterialTheme.colorScheme.onBackground,
    secondColor: Color = MaterialTheme.colorScheme.tertiary
) {
    val infinite = rememberInfiniteTransition(label = "clock")
    val secondAngle by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(60000, easing = LinearEasing)),
        label = "secondHand"
    )
    val minuteAngle by infinite.animateFloat(
        initialValue = 120f,
        targetValue = 480f,
        animationSpec = infiniteRepeatable(tween(3600000, easing = LinearEasing)),
        label = "minuteHand"
    )
    val hourAngle = 305f + minuteAngle / 12f

    Canvas(modifier = modifier.aspectRatio(1f)) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        for (i in 0 until 12) {
            rotate(i * 30f, center) {
                drawLine(
                    color = hourColor.copy(alpha = 0.65f),
                    start = Offset(center.x, center.y - radius * 0.9f),
                    end = Offset(center.x, center.y - radius * 0.78f),
                    strokeWidth = 6.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
        for (i in 0 until 60) {
            if (i % 5 != 0) {
                rotate(i * 6f, center) {
                    drawLine(
                        color = minuteColor.copy(alpha = 0.35f),
                        start = Offset(center.x, center.y - radius * 0.9f),
                        end = Offset(center.x, center.y - radius * 0.84f),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }

        drawClockHand(center, hourAngle, radius * 0.48f, hourColor, 6.dp.toPx())
        drawClockHand(center, minuteAngle, radius * 0.66f, minuteColor, 4.dp.toPx())
        drawClockHand(center, secondAngle, radius * 0.78f, secondColor, 2.dp.toPx())
        drawCircle(color = secondColor, radius = 6.dp.toPx(), center = center)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawClockHand(
    center: Offset,
    angle: Float,
    length: Float,
    color: Color,
    strokeWidth: Float
) {
    val angleRad = (angle - 90) * (PI / 180f).toFloat()
    drawLine(
        color = color,
        start = center,
        end = Offset(center.x + length * cos(angleRad), center.y + length * sin(angleRad)),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

@Composable
fun VinylDiskRotating(
    modifier: Modifier = Modifier,
    diskColor: Color = Color.Black
) {
    val labelColor = MaterialTheme.colorScheme.primary
    val holeColor = MaterialTheme.colorScheme.surface
    val infinite = rememberInfiniteTransition(label = "vinyl")
    val angle by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing)),
        label = "vinylAngle"
    )

    Canvas(modifier = modifier.aspectRatio(1f).rotate(angle)) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        drawCircle(diskColor, radius, center)
        drawCircle(
            brush = Brush.sweepGradient(
                listOf(
                    Color.White.copy(alpha = 0.16f),
                    Color.Transparent,
                    Color.White.copy(alpha = 0.08f),
                    Color.Transparent
                )
            ),
            radius = radius,
            center = center
        )
        for (i in 1..8) {
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = radius * i / 9f,
                center = center,
                style = Stroke(width = 1f)
            )
        }
        drawCircle(labelColor, radius * 0.28f, center)
        drawCircle(holeColor, radius * 0.04f, center)
    }
}

@Composable
fun HeartButton(
    modifier: Modifier = Modifier,
    isInitiallyLiked: Boolean = false,
    burstCount: Int = 6,
    burstDuration: Int = 800,
    activeColor: Color = Color.Red,
    defaultColor: Color = MaterialTheme.colorScheme.primary,
    onLikeChanged: (Boolean) -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(isInitiallyLiked) }
    var triggerBurst by remember { mutableStateOf(false) }
    var burstHearts by remember { mutableStateOf(emptyList<BurstHeart>()) }
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        finishedListener = { if (isLiked) triggerBurst = true },
        label = "heartScale"
    )

    LaunchedEffect(triggerBurst) {
        if (triggerBurst) {
            burstHearts = List(burstCount) {
                BurstHeart(Random.nextFloat() * 360f, Random.nextInt(40, 120).toFloat())
            }
            triggerBurst = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth().height(100.dp)
    ) {
        burstHearts.forEach { heart ->
            val alpha = remember(heart) { Animatable(1f) }
            val progress = remember(heart) { Animatable(0f) }
            LaunchedEffect(heart) {
                launch { progress.animateTo(1f, tween(burstDuration, easing = FastOutSlowInEasing)) }
                launch { alpha.animateTo(0f, tween(burstDuration, easing = LinearEasing)) }
                delay(burstDuration.toLong())
                burstHearts = burstHearts - heart
            }
            val x = heart.distance * progress.value * cos(heart.angle / 180f * PI.toFloat())
            val y = heart.distance * progress.value * sin(heart.angle / 180f * PI.toFloat())
            Text(
                text = "♥",
                color = activeColor,
                fontSize = 20.sp,
                modifier = Modifier.offset { IntOffset(x.toInt(), y.toInt()) }.alpha(alpha.value)
            )
        }

        Text(
            text = if (isLiked) "♥" else "♡",
            color = if (isLiked) activeColor else defaultColor,
            fontSize = 56.sp,
            modifier = Modifier
                .scale(scale)
                .clickable {
                    isLiked = !isLiked
                    onLikeChanged(isLiked)
                    if (isLiked) triggerBurst = true
                }
        )
    }
}

private data class BurstHeart(val angle: Float, val distance: Float)

@Composable
fun RatingStars(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    iconSize: TextUnit = 40.sp,
    itemSpacing: Dp = 8.dp
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(itemSpacing)) {
        repeat(5) { index ->
            val selected = index < rating
            Text(
                text = if (selected) "★" else "☆",
                fontSize = iconSize,
                color = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable { onRatingChange(index + 1) }
            )
        }
    }
}

@Composable
fun BetterCarousel(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState { colors.size },
    itemContent: @Composable BoxScope.(Color) -> Unit = {}
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp),
        pageSpacing = 10.dp,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(3)
        ),
        modifier = modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    scaleX = scale
                    scaleY = scale
                    alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                }
        ) {
            itemContent(colors[page])
        }
    }
}

@Composable
fun CalendarTopBar(
    modifier: Modifier = Modifier,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..7).forEach { day ->
            val selected = day == selectedDay
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                    .clickable { onDaySelected(day) }
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[day - 1],
                    color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
                Text(
                    text = (10 + day).toString(),
                    color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AudioPlayerCard(modifier: Modifier = Modifier) {
    var isPlaying by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VinylDiskRotating(Modifier.size(72.dp), diskColor = MaterialTheme.colorScheme.onSurface)
            Column(modifier = Modifier.weight(1f)) {
                Text("Clube da Esquina", fontWeight = FontWeight.Bold)
                Text("CMP-safe audio player shell", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = { isPlaying = !isPlaying }) {
                Text(if (isPlaying) "Pause" else "Play")
            }
        }
    }
}

@Composable
fun NeumorphismDemo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NeuMorphicBox(Modifier.weight(1f).aspectRatio(1f), raised = true)
        NeuMorphicBox(Modifier.weight(1f).aspectRatio(1f), raised = false)
        NeuMorphicBox(Modifier.weight(1f).aspectRatio(1f), shape = CircleShape, raised = true)
        NeuMorphicBox(Modifier.weight(1f).aspectRatio(1f), shape = CircleShape, raised = false)
    }
}

@Composable
fun NeuMorphicBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    raised: Boolean = true,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val base = MaterialTheme.colorScheme.surfaceVariant
    Box(
        modifier = modifier
            .shadow(
                elevation = if (raised) 10.dp else 2.dp,
                shape = shape,
                ambientColor = Color.White.copy(alpha = if (raised) 0.55f else 0.18f),
                spotColor = Color.Black.copy(alpha = if (raised) 0.22f else 0.08f)
            )
            .background(base, shape)
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = if (raised) 0.25f else 0.08f)),
                shape
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun CorneredBox(
    modifier: Modifier = Modifier,
    cornerColor: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = Color.Transparent,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    cornerStrokeWidth: Dp = 6.dp,
    cornerSize: Dp = 24.dp,
    shape: Shape = RoundedCornerShape(10.dp),
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(containerColor, shape)
            .clip(shape)
            .drawBehind {
                val stroke = cornerStrokeWidth.toPx()
                val length = cornerSize.toPx()
                val w = size.width
                val h = size.height
                drawLine(cornerColor, Offset(0f, 0f), Offset(length, 0f), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(0f, 0f), Offset(0f, length), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(w, 0f), Offset(w - length, 0f), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(w, 0f), Offset(w, length), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(0f, h), Offset(length, h), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(0f, h), Offset(0f, h - length), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(w, h), Offset(w - length, h), stroke, cap = StrokeCap.Round)
                drawLine(cornerColor, Offset(w, h), Offset(w, h - length), stroke, cap = StrokeCap.Round)
            }
            .padding(contentPadding),
        contentAlignment = contentAlignment,
        content = content
    )
}

@Composable
fun MovieTicket(modifier: Modifier = Modifier, cutoutFraction: Float = 0.7f) {
    Ticket(modifier = modifier, cutoutFraction = cutoutFraction) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF111827), Color(0xFF7C2D12), Color(0xFFFBBF24))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("MOVIE NIGHT", color = Color.White, fontWeight = FontWeight.Black, fontSize = 28.sp)
        }
    }
}

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    cutoutFraction: Float = 0.7f,
    topContent: @Composable BoxScope.() -> Unit
) {
    val shape = VerticalTicketShape(cornerRadiusPercent = 10, cutoutRadius = 10.dp, cutoutHeightFraction = cutoutFraction)
    Column(modifier = modifier.background(color, shape).clip(shape)) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(cutoutFraction), content = topContent)
        DottedDivider()
        Column(modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TicketMeta("Date", "Dec 25")
                TicketMeta("Time", "6pm")
                TicketMeta("Seat", "R4 5-7")
            }
            Spacer(Modifier.height(14.dp))
            RandomBarcode(Modifier.fillMaxWidth().height(48.dp))
        }
    }
}

@Composable
private fun TicketMeta(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

class VerticalTicketShape(
    private val cornerRadiusPercent: Int = 10,
    private val cutoutRadius: Dp = 16.dp,
    private val cutoutHeightFraction: Float = 0.80f
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        with(density) {
            val cornerRadiusPx = min(size.width, size.height) * (cornerRadiusPercent / 100f)
            val cutoutRadiusPx = cutoutRadius.toPx()
            val ticketPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                    )
                )
            }
            val cutoutCenterY = size.height * cutoutHeightFraction
            val leftCutout = Path().apply {
                addOval(Rect(-cutoutRadiusPx, cutoutCenterY - cutoutRadiusPx, cutoutRadiusPx, cutoutCenterY + cutoutRadiusPx))
            }
            val rightCutout = Path().apply {
                addOval(Rect(size.width - cutoutRadiusPx, cutoutCenterY - cutoutRadiusPx, size.width + cutoutRadiusPx, cutoutCenterY + cutoutRadiusPx))
            }
            return Outline.Generic(
                Path().apply {
                    op(ticketPath, leftCutout, PathOperation.Difference)
                    op(this, rightCutout, PathOperation.Difference)
                }
            )
        }
    }
}

@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    dashLength: Dp = 12.dp,
    gap: Dp = 8.dp,
    lineColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
    strokeWidth: Dp = 2.dp
) {
    Canvas(modifier = modifier.fillMaxWidth().height(strokeWidth)) {
        var x = 0f
        val y = size.height / 2f
        while (x < size.width) {
            drawLine(lineColor, Offset(x, y), Offset(x + dashLength.toPx(), y), strokeWidth.toPx())
            x += dashLength.toPx() + gap.toPx()
        }
    }
}

@Composable
fun RandomBarcode(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    count: Int = 35
) {
    val bars = remember(count) { List(count) { (1..4).random() * 4 to (1..4).random() * 4 } }
    Canvas(modifier = modifier) {
        val totalBarsWidth = bars.sumOf { it.first + it.second }
        var x = (size.width - totalBarsWidth) / 2f
        bars.forEach { bar ->
            drawRect(color = color, topLeft = Offset(x, 0f), size = Size(bar.first.toFloat(), size.height))
            x += bar.first + bar.second
        }
    }
}

@Composable
fun Receipt(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, ReceiptShape(amplitude = 18f, cycles = 18, cornerRadius = 18f))
            .padding(horizontal = 20.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("CASH RECEIPT", color = Color(0xFF404C65), fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(20.dp))
        DottedDivider(lineColor = Color(0xFFCBD5E1), dashLength = 4.dp, gap = 4.dp)
        Spacer(Modifier.height(18.dp))
        ReceiptRow("Date", "01/01/2003")
        ReceiptRow("Manager", "Ben Parker")
        ReceiptRow("Cashier", "Peter Parker")
        Spacer(Modifier.height(16.dp))
        DottedDivider(lineColor = Color(0xFFCBD5E1), dashLength = 4.dp, gap = 4.dp)
        Spacer(Modifier.height(18.dp))
        ReceiptRow("Coffee", "4.50 EUR")
        ReceiptRow("Carrot Cake", "8.50 EUR")
        ReceiptRow("Total", "13.52 EUR", bold = true)
    }
}

@Composable
private fun ReceiptRow(key: String, value: String, bold: Boolean = false) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(key, color = Color(0xFF404C65), fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
        Text(value, color = Color(0xFF404C65), fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal)
    }
}

class ReceiptShape(
    private val amplitude: Float = 12f,
    private val cycles: Int = 10,
    private val cornerRadius: Float = 12f,
    private val samplingStepPx: Int = 1
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val w = size.width
        val h = size.height
        val cr = cornerRadius.coerceAtMost(min(w, h) / 2f)
        val usableLeft = cr
        val usableRight = w - cr
        val usableW = maxOf(0.0001f, usableRight - usableLeft)
        val k = maxOf(1, cycles)
        val wavelength = usableW / k
        val totalPoints = maxOf(3, (wavelength / samplingStepPx).toInt()) * k

        fun topYAt(x: Float): Float {
            val phase = 2.0 * PI * k * ((x - usableLeft) / usableW)
            return (amplitude * (1 - cos(phase)) / 2).toFloat()
        }
        fun bottomYAt(x: Float): Float = h - topYAt(x)

        return Outline.Generic(
            Path().apply {
                moveTo(0f, cr)
                quadraticTo(0f, 0f, usableLeft, 0f)
                for (i in 0..totalPoints) {
                    val x = usableLeft + i * usableW / totalPoints
                    lineTo(x, topYAt(x))
                }
                quadraticTo(w, 0f, w, cr)
                lineTo(w, h - cr)
                quadraticTo(w, h, w - cr, h)
                for (i in totalPoints downTo 0) {
                    val x = usableLeft + i * usableW / totalPoints
                    lineTo(x, bottomYAt(x))
                }
                quadraticTo(0f, h, 0f, h - cr)
                close()
            }
        )
    }
}

@Composable
fun DepthCardDemo(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color(0xFFFFCEB1),
        Color(0xFFD6E5BD),
        Color(0xFFF9E1A8)
    )
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        colors.forEachIndexed { index, color ->
            DepthCard(color = color, label = "0${index + 1}", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun DepthCard(color: Color, label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(144.dp).background(shape = RoundedCornerShape(16.dp), color = color),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.width(82.dp).height(124.dp).offset(y = (-22).dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(listOf(color.copy(alpha = 0.5f), MaterialTheme.colorScheme.surface))
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(label, fontWeight = FontWeight.Black, fontSize = 28.sp)
            }
        }
    }
}

@Composable
fun StackableItemDemo(modifier: Modifier = Modifier) {
    val colors = listOf(
        Color.White.copy(0.8f),
        MaterialTheme.colorScheme.primary.copy(0.45f),
        MaterialTheme.colorScheme.secondary.copy(0.45f),
        MaterialTheme.colorScheme.tertiary.copy(0.45f)
    )
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        colors.forEachIndexed { index, color ->
            StackableItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .offset(y = (index * 34).dp)
                    .graphicsLayer {
                        scaleX = 1f - index * 0.04f
                        scaleY = 1f - index * 0.04f
                    },
                color = color,
                index = index
            )
        }
    }
}

@Composable
fun StackableItem(
    modifier: Modifier = Modifier,
    color: Color,
    index: Int
) {
    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(18.dp))
            .background(color, RoundedCornerShape(18.dp))
            .padding(18.dp)
    ) {
        Text("Stack item ${index + 1}", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StepProgressDemo(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 5
    val stepContent = remember {
        listOf(
            "Welcome to your journey.",
            "Set up preferences.",
            "Review configuration.",
            "Confirm everything.",
            "All steps complete."
        )
    }
    val stepTitles = remember {
        listOf("Start", "Setup", "Configure", "Review", "Done")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressTick(
            totalSteps = totalSteps,
            currentStep = currentStep,
            stepLabels = stepTitles,
            onStepClick = { step -> currentStep = step }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Crossfade(
                targetState = currentStep,
                animationSpec = tween(500),
                label = "step_transition"
            ) { targetStep ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stepTitles.getOrElse(targetStep - 1) { "Step $targetStep" },
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stepContent.getOrElse(targetStep - 1) { "" },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = {
                currentStep = if (currentStep < totalSteps) currentStep + 1 else 1
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (currentStep < totalSteps) "Continue" else "Start over")
        }
    }
}

@Composable
private fun ProgressTick(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onStepClick: (Int) -> Unit,
    stepLabels: List<String> = emptyList()
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(totalSteps) { index ->
                val step = index + 1
                val isActive = step <= currentStep
                val isCurrent = step == currentStep
                val color by animateColorAsState(
                    targetValue = if (isActive) activeColor else inactiveColor,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "stepColor"
                )
                val scale by animateFloatAsState(
                    targetValue = if (isCurrent) 1.1f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "stepScale"
                )
                val elevation by animateDpAsState(
                    targetValue = if (isCurrent) 8.dp else if (isActive) 4.dp else 0.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "stepElevation"
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .shadow(
                            elevation = elevation,
                            shape = CircleShape,
                            ambientColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent,
                            spotColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent
                        )
                        .background(color = color, shape = CircleShape)
                        .clickable { onStepClick(step) }
                ) {
                    Text(
                        text = if (isActive) "ok" else step.toString(),
                        color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }

                if (step != totalSteps) {
                    val lineColor by animateColorAsState(
                        targetValue = if (step < currentStep) activeColor else inactiveColor,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "lineColor"
                    )
                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(lineColor)
                    )
                }
            }
        }

        if (stepLabels.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(totalSteps) { index ->
                    val step = index + 1
                    val isActive = step <= currentStep
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stepLabels.getOrNull(index) ?: "Step $step",
                            color = if (isActive) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            },
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}
