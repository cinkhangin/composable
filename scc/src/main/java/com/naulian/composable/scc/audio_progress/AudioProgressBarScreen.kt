package com.naulian.composable.scc.audio_progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.White
import com.naulian.neumorphic.ComposableTheme
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AudioPlayerScreen() {
    val navController = LocalNavController.current
    AudioPlayerScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun AudioPlayerScreenUI(
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Audio Player",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->

        val cafeReceiptSource = remember { audioPlayer }
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            AudioPlayer()

            CodeBlock(
                source = cafeReceiptSource,
                language = "kotlin"
            )
        }
    }
}

@Composable
fun AudioPlayer(
    modifier: Modifier = Modifier
) {

    val radius = 140f
    val stroke = 16f
    val circumference = 2 * Math.PI * radius
    val progress = 50f

    Box(
            modifier = Modifier
                .size((radius * 2 + stroke * 2 ).dp)
                .padding(5.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFFA3B1C6).copy(alpha = 0.5f),
                    spotColor = Color.White.copy(alpha = 0.8f)
                )
                .neumorphicUpAP(shape = CircleShape, shadowPadding = 30.dp),
            contentAlignment = Alignment.Center
        ) {

            Canvas(modifier = Modifier.fillMaxSize().padding(2.dp)) {

                val center = Offset(size.width / 2, size.height / 2)
                val radiusPx = min(size.width, size.height) / 2 - stroke



                drawCircle(
                    color = Color(0xFFD7E2EA),
                    radius = radiusPx,
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )



                drawArc(
                    color = Color.Black,
                    startAngle = -90f,
                    sweepAngle = 360 * (progress / 100f),
                    useCenter = false,
                    style = Stroke(width = stroke, cap = StrokeCap.Round),
                    size = Size(radiusPx * 2, radiusPx * 2),
                    topLeft = Offset(center.x - radiusPx, center.y - radiusPx)
                )

                // Thumb position
                val angleRad = Math.toRadians((progress / 100f) * 360.0 - 90)
                val thumbX = center.x + radiusPx * cos(angleRad).toFloat()
                val thumbY = center.y + radiusPx * sin(angleRad).toFloat()
                drawCircle(
                    color = Color.Black,
                    radius = stroke * 1.3f,
                    center = Offset(thumbX, thumbY)
                )


            }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    ControlsButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Backward",
                            tint = Color.Gray
                        )
                    }

                    ControlsButton(onClick = {  }, size = 90.dp) {
                        if (true) {
                            Icon(
                                imageVector = Icons.Default.Pause,
                                contentDescription = "Pause",
                                tint = Color.Black,
                                modifier = Modifier.size(36.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.Black,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    ControlsButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Forward",
                            tint = Color.Gray
                        )
                    }
                }

            }


    }

}

@Composable
private fun Modifier.neumorphicUpAP(
    shape: Shape,
    shadowPadding: Dp,
) = background(
    color = Color.White,
    shape = shape
)
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = Color(0xFFF9F9F9),
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )
    .innerShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = Color(0xFFDADADA),
            offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
        )
    )



@Composable
fun ControlsButton(
    onClick: () -> Unit,
    size: Dp = 60.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .background(Color.White, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
        content = content
    )
}




@Preview
@Composable
fun ReceiptScreenPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            AudioPlayerScreenUI()
        }
    }
}

val audioPlayer = """
    @Composable
    fun AudioPlayer(
        modifier: Modifier = Modifier
    ) {

        val radius = 160f
        val stroke = 16f
        val circumference = 2 * Math.PI * radius
        val progress = 50f

        Box(
            modifier = Modifier
                .size((radius * 2 + stroke * 2 ).dp)
                .padding(5.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFFA3B1C6).copy(alpha = 0.5f),
                    spotColor = Color.White.copy(alpha = 0.8f)
                )
                .neumorphicUpAP(shape = CircleShape, shadowPadding = 30.dp),
            contentAlignment = Alignment.Center
            ) {

                Canvas(modifier = Modifier.fillMaxSize().padding(2.dp)) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radiusPx = min(size.width, size.height) / 2 - stroke
                    drawCircle(
                        color = Color(0xFFD7E2EA),
                        radius = radiusPx,
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = Color.Black,
                        startAngle = -90f,
                        sweepAngle = 360 * (progress / 100f),
                        useCenter = false,
                        style = Stroke(width = stroke, cap = StrokeCap.Round),
                        size = Size(radiusPx * 2, radiusPx * 2),
                        topLeft = Offset(center.x - radiusPx, center.y - radiusPx)
                    )

                    // Thumb position
                    val angleRad = Math.toRadians((progress / 100f) * 360.0 - 90)
                    val thumbX = center.x + radiusPx * cos(angleRad).toFloat()
                    val thumbY = center.y + radiusPx * sin(angleRad).toFloat()
                    drawCircle(
                        color = Color.Black,
                        radius = stroke * 1.3f,
                        center = Offset(thumbX, thumbY)
                    )


                }


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        ControlsButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.SkipPrevious,
                                contentDescription = "Backward",
                                tint = Color.Gray
                            )
                        }

                        ControlsButton(onClick = {  }, size = 90.dp) {
                            if (true) {
                                Icon(
                                    imageVector = Icons.Default.Pause,
                                    contentDescription = "Pause",
                                    tint = Color.Black,
                                    modifier = Modifier.size(36.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.Black,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        ControlsButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.SkipNext,
                                contentDescription = "Forward",
                                tint = Color.Gray
                            )
                        }
                    }

                }


        }

    }

    @Composable
    private fun Modifier.neumorphicUpAP(
        shape: Shape,
        shadowPadding: Dp,
    ) = background(
        color = Color.White,
        shape = shape
    )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = Color(0xFFF9F9F9),
                offset = DpOffset(x = shadowPadding, y = shadowPadding)
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = Color(0xFFDADADA),
                offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
            )
        )



    @Composable
    fun ControlsButton(
        onClick: () -> Unit,
        size: Dp = 60.dp,
        content: @Composable BoxScope.() -> Unit
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .shadow(elevation = 6.dp, shape = CircleShape)
                .background(Color.White, CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
            content = content
        )
    }

""".trimIndent()