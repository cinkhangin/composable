package com.naulian.composable.scc.glass

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.columnItem
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GlassCardScreen() {
    val navController = LocalNavController.current

    GlassCardScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GlassCardScreenUI(onBack: () -> Unit = {}) {
    val code = remember { GlassDashboard }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedGradient by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Glass Card",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF141E30),
                                    Color(0xFF243B55),
                                    Color(0xFF141E30)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(
                                    x = 1000f * sin(animatedGradient * Math.PI.toFloat()).absoluteValue,
                                    y = 1000f * cos(animatedGradient * Math.PI.toFloat()).absoluteValue
                                )
                            )
                        )
                        .padding(20.dp)
                ) {

                    AnimatedParticles(particleCount = 15)

                    GlassCard(width = 340.dp, height = 140.dp) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                "Upcoming Events",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                "• Team Meeting - 10:00 AM",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                            Text(
                                "• Client Call - 2:00 PM",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                            Text(
                                "• Review - 5:30 PM",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }

                }

                CodeBlock(
                    source = code,
                    language = "kotlin"
                )
            }
        }
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

    val infiniteTransition = rememberInfiniteTransition()
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .graphicsLayer {
                translationY = floatingOffset
            }
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

@Composable
fun Modifier.liquidGlassEffect(
    shape: Shape,
    borderColor: Color,
    intensity: Float = 0.7f,
    blurRadius: Dp = 0.dp
): Modifier {
    return this
        .clip(shape)
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

        .then(if (blurRadius > 0.dp) Modifier.blur(blurRadius) else Modifier)
}


@Composable
fun AnimatedParticles(particleCount: Int) {
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

data class Particle(
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
        speed = Random.nextFloat() * (1.5f - 0.2f) + 0.2f
        direction = (0..360).random().toFloat()
        opacity = Random.nextFloat() * (0.4f - 0.1f) + 0.1f
        rotation = (0..360).random().toFloat()
        rotationSpeed = Random.nextFloat() * (2f - (-2f)) + (-2f)
        return Offset(x, y)
    }

    fun update(): Offset {
        x += cos(Math.toRadians(direction.toDouble())).toFloat() * speed
        y += sin(Math.toRadians(direction.toDouble())).toFloat() * speed
        rotation += rotationSpeed


        if (x < -100f || x > 1100f || y < -100f || y > 2100f) {
            reset()
        }

        return Offset(x, y)
    }
}

@Preview
@Composable
private fun GlassDashboardScreenPreview() {
    ComposableTheme {
        GlassCardScreen()
    }
}

val GlassDashboard by lazy {
    """
        @Composable
        fun LiquidGlassCard(
            title: String? = null,
            value: String? = null,
            width: Dp,
            height: Dp,
            showProgressBar: Boolean = false,
            content: @Composable (() -> Unit)? = null
        ) {
        
            val infiniteTransition = rememberInfiniteTransition()
            val floatingOffset by infiniteTransition.animateFloat(
                initialValue = -2f,
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        
            Box(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .graphicsLayer {
                        translationY = floatingOffset
                    }
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
                        if (showProgressBar) {
                            Spacer(Modifier.height(12.dp))
                            LiquidProgressBar(progress = 0.85f)
                        }
                    }
                }
            }
        }
    """.trimIndent()
}