package com.ckgin.composable.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ckgin.composable.component.icc.AudioPlayerComponent

@Composable
actual fun AudioPlayerDemo(modifier: Modifier) {
    AudioPlayerComponent(modifier)
}
