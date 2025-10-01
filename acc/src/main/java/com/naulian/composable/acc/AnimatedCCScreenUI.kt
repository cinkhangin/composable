package com.naulian.composable.acc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.LazyItemList
import com.naulian.composable.core.model.ComponentItem

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data class Navigate(val route: Screen) : AccUIEvent
}

private val accItemList = listOf(
    ComponentItem(
        name = "Typing Text",
        contributor = "Shree Bhargav R K",
        route = Screen.TypingText,
        component = { EmptyComponent(it) }
    ),
    ComponentItem(
        name = "Pulse Heart",
        contributor = "Shree Bhargav R K",
        route = Screen.PulseHeart,
        component = { EmptyComponent(it) }
    ),
    ComponentItem(
        name = "Glitch Effect",
        contributor = "Shree Bhargav R K",
        route = Screen.GlitchEffect,
        component = { EmptyComponent(it) }
    ),
    ComponentItem(
        name = "Analog Clock",
        contributor = "Naulian",
        route = Screen.Clock,
        component = { EmptyComponent(it) }
    ),
    ComponentItem(
        name = "Animated Counter",
        contributor = "Eleazar Cole-Showers",
        route = Screen.Counter,
        component = { EmptyComponent(it) }
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