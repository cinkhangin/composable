package com.naulian.composable.icc.audio_player

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.createAttributionContext
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.icc.R
import com.naulian.modify.White
import com.naulian.neumorphic.ComposableTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun AudioPlayerScreen() {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val audioAttributionContext: Context =
        if (Build.VERSION.SDK_INT >= 30) createAttributionContext(
            context,
            "audioPlayback"
        ) else context

    val player = remember {
        MediaPlayer.create(audioAttributionContext, R.raw.music)
    }

    var currentPosition by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(player) {
        while (true) {
            delay(100)
            currentPosition = player.currentPosition
            isPlaying = player.isPlaying
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.stop()
            player.release()
        }
    }

    AudioPlayerScreenUI(
        isPlaying = isPlaying,
        progress = { currentPosition.toFloat() / player.duration }
    ) {
        when (it) {
            AudioPlayerUIEvent.Back -> navController.navigateUp()
            AudioPlayerUIEvent.Pause -> player.pause()
            AudioPlayerUIEvent.Play -> player.start()
        }
    }
}

sealed interface AudioPlayerUIEvent {
    object Play : AudioPlayerUIEvent
    object Pause : AudioPlayerUIEvent
    object Back : AudioPlayerUIEvent
}

@Composable
fun AudioPlayerScreenUI(
    isPlaying: Boolean = false,
    progress: () -> Float = { 0f },
    uiEvent: (AudioPlayerUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Audio Player",
                onBack = { uiEvent(AudioPlayerUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->

        val audioPlayerCode = remember { audioPlayerCode }

        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            AudioPlayer(
                isPlaying = isPlaying,
                progress = { progress() },
                onClickPlay = { uiEvent(AudioPlayerUIEvent.Play) },
                onClickPause = { uiEvent(AudioPlayerUIEvent.Pause) }
            )

            CodeBlock(
                source = audioPlayerCode,
                language = "kotlin"
            )
        }
    }
}

@Composable
fun AudioPlayer(
    isPlaying: Boolean,
    progress: () -> Float,
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit = {},
    onClickPause: () -> Unit = {}
) {

    val radius = 140f
    val stroke = 16f

    Box(
        modifier = modifier
            .size((radius * 2 + stroke * 2).dp)
            .padding(5.dp)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                ambientColor = Color(0xFFA3B1C6).copy(alpha = 0.5f),
                spotColor = Color.White.copy(alpha = 0.8f)
            )
            .background(White),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {

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
                sweepAngle = 360 * progress(),
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
                size = Size(radiusPx * 2, radiusPx * 2),
                topLeft = Offset(center.x - radiusPx, center.y - radiusPx)
            )

            // Thumb position
            val angleRad = Math.toRadians(progress() * 360.0 - 90)
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
                ControlsButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Backward",
                        tint = Color.Gray
                    )
                }

                ControlsButton(
                    onClick = { if (isPlaying) onClickPause() else onClickPlay() },
                    size = 90.dp
                ) {
                    if (isPlaying) {
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

                ControlsButton(onClick = { }) {
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

val audioPlayerCode = """
@Composable
fun AudioPlayer(
    isPlaying: Boolean,
    progress: () -> Float,
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit = {},
    onClickPause: () -> Unit = {}
) {

    val radius = 140f
    val stroke = 16f

    Box(
        modifier = modifier
            .size((radius * 2 + stroke * 2).dp)
            .padding(5.dp)
            .shadow(
                elevation = 10.dp,
                shape = CircleShape,
                ambientColor = Color(0xFFA3B1C6).copy(alpha = 0.5f),
                spotColor = Color.White.copy(alpha = 0.8f)
            )
            .background(White),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {

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
                sweepAngle = 360 * progress(),
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round),
                size = Size(radiusPx * 2, radiusPx * 2),
                topLeft = Offset(center.x - radiusPx, center.y - radiusPx)
            )

            // Thumb position
            val angleRad = Math.toRadians(progress() * 360.0 - 90)
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
                ControlsButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Backward",
                        tint = Color.Gray
                    )
                }

                ControlsButton(
                    onClick = { if (isPlaying) onClickPause() else onClickPlay() },
                    size = 90.dp
                ) {
                    if (isPlaying) {
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

                ControlsButton(onClick = { }) {
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
""".trimIndent()