package com.naulian.composable.acc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import com.naulian.composable.acc.component.BubbleRise
import com.naulian.composable.acc.component.Clock
import com.naulian.composable.acc.component.GlitchText
import com.naulian.composable.acc.component.PulseAnimation
import com.naulian.composable.acc.component.RadarAnimation
import com.naulian.composable.acc.component.RadarCode
import com.naulian.composable.acc.component.TypingText
import com.naulian.composable.acc.component.VinylDiskRotating
import com.naulian.composable.acc.component.bubbleRiseCode
import com.naulian.composable.acc.component.clockCode
import com.naulian.composable.acc.component.glitchCode
import com.naulian.composable.acc.component.pulseCode
import com.naulian.composable.acc.component.typingCode
import com.naulian.composable.acc.component.vinylDiskCode
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableComponent

@Composable
fun EntryProviderScope<Screen>.AnimatedCCScreen(backStack: SnapshotStateList<Screen>) {
    entry<Screen.AnimatedCC> {
        AnimatedCCScreenUI {
            when (it) {
                AccUIEvent.Back -> backStack.removeLastOrNull()
                is AccUIEvent.Navigate -> backStack.add(
                    Screen.ComposableScreen(it.id)
                )
            }
        }
    }
}

val accItemList = listOf(
    ComposableComponent(
        id = "Typing Text",
        name = "Typing Text",
        contributor = "Shree Bhargav R K",
        previewComponent = { TypingTextComponent(it) },
        demoComponent = {
            TypingText(text = "This is a typing text animation")
        },
        sourceCode = typingCode
    ),
    ComposableComponent(
        id = "Pulse Animation",
        name = "Pulse Animation",
        contributor = "Shree Bhargav R K",
        previewComponent = { PulseComponent(it) },
        demoComponent = {
            PulseAnimation(
                modifier = it
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        },
        sourceCode = pulseCode
    ),
    ComposableComponent(
        id = "Glitch Effect",
        name = "Glitch Effect",
        contributor = "Shree Bhargav R K",
        previewComponent = { GlitchEffectComponent(it) },
        demoComponent = {
            GlitchText()
        },
        sourceCode = glitchCode
    ),
    ComposableComponent(
        id = "Analog Clock",
        name = "Analog Clock",
        contributor = "Naulian",
        previewComponent = { ClockComponent(it) },
        demoComponent = {
            Clock(modifier = Modifier.fillMaxWidth())
        },
        sourceCode = clockCode
    ),
    ComposableComponent(
        id = "Bubble Rise",
        name = "Bubble Rise",
        contributor = "Eleazar Cole-Showers",
        previewComponent = { BubblesComponent(it) },
        sourceCode = bubbleRiseCode,
        demoComponent = {
            Column(
                modifier = it
                    .fillMaxWidth()
                    .height(400.dp), // limit height for demo
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BubbleRise(modifier = Modifier.fillMaxSize())
            }
        }
    ),
    ComposableComponent(
        id = "Radar Effect",
        name = "Radar Effect",
        contributor = "Samarth",
        previewComponent = { RadarEffectComponent(it) },
        demoComponent = {
            RadarAnimation(modifier = Modifier.align(Alignment.CenterHorizontally))
        },
        sourceCode = RadarCode
    ),
    ComposableComponent(
        id = "Vinyl Disk",
        name = "Vinyl Disk",
        contributor = "Donizete Vida",
        previewComponent = { VinylComponent(modifier = it) },
        demoComponent = {
            VinylDiskRotating(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F),
                needleColors = listOf(
                    Color(0x99444444),
                    Color(0x22999999),
                    Color(0x99444444),
                    Color(0xFFEAD961),
                    Color(0x59444444),
                    Color(0x12999999),
                    Color(0x997CFF58),
                    Color(0x0D999999),
                )
            )
        },
        sourceCode = vinylDiskCode
    )
)