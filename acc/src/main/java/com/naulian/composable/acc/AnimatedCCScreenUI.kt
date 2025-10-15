package com.naulian.composable.acc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.acc.vinyl_disk.VinylDiskRotating
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.LazyItemList
import com.naulian.composable.core.model.ComponentItem

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data class Navigate(val route: Screen) : AccUIEvent
}

val accItemList = listOf(
    ComponentItem(
        name = "Typing Text",
        contributor = "Shree Bhargav R K",
        route = Screen.TypingText,
        component = { TypingTextComponent(it) }
    ),
    ComponentItem(
        name = "Pulse Animation",
        contributor = "Shree Bhargav R K",
        route = Screen.PulseHeart,
        component = { PulseComponent(it) }
    ),
    ComponentItem(
        name = "Glitch Effect",
        contributor = "Shree Bhargav R K",
        route = Screen.GlitchEffect,
        component = { GlitchEffectComponent(it) }
    ),
    ComponentItem(
        name = "Analog Clock",
        contributor = "Naulian",
        route = Screen.Clock,
        component = { ClockComponent(it) }
    ),
    ComponentItem(
        name = "Bubble Rise",
        contributor = "Eleazar Cole-Showers",
        route = Screen.Bubble,
        component = { BubblesComponent(it) }
    ),
    ComponentItem(
        name = "Vinyl Disk",
        contributor = "Donizete Vida",
        route = Screen.VinylDisk,
        component = { VinylComponent(modifier = it) }
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCCScreenUI(
    uiEvent: (AccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Animated Components",
                onBack = { uiEvent(AccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        LazyItemList(
            items = accItemList,
            onClickItem = { uiEvent(AccUIEvent.Navigate(it.route)) },
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
        )
    }
}