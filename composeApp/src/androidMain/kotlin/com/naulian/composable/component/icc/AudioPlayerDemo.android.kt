package com.naulian.composable.component.icc

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.naulian.composable.cmp.R
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
actual fun AudioPlayerDemo(modifier: Modifier) {
    val context = LocalContext.current
    val player = remember(context) {
        checkNotNull(MediaPlayer.create(context, R.raw.music))
    }
    var currentPosition by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(player) {
        while (currentCoroutineContext().isActive) {
            currentPosition = player.currentPosition
            isPlaying = player.isPlaying
            delay(100)
        }
    }

    DisposableEffect(player) {
        onDispose { player.release() }
    }

    AudioPlayer(
        isPlaying = isPlaying,
        progress = {
            if (player.duration > 0) currentPosition.toFloat() / player.duration else 0f
        },
        modifier = modifier,
        onClickPlay = { player.start() },
        onClickPause = { player.pause() }
    )
}
