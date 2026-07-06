package com.naulian.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
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
