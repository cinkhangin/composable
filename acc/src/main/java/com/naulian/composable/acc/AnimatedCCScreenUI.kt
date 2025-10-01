package com.naulian.composable.acc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.ListItemText
import com.naulian.composable.core.model.ComponentItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data class Navigate(val route: Screen) : AccUIEvent
}

private val animatedCCItems = listOf(
    ComponentItem(
        name = "Typing Text",
        contributor = "Shree Bhargav R K",
        route = Screen.TypingText
    ),
    ComponentItem(
        name = "Pulse Heart",
        contributor = "Shree Bhargav R K",
        route = Screen.PulseHeart
    ),
    ComponentItem(
        name = "Glitch Effect",
        contributor = "Shree Bhargav R K",
        route = Screen.GlitchEffect
    ),
    ComponentItem(
        name = "Analog Clock",
        contributor = "Naulian",
        route = Screen.Clock
    ),
    ComponentItem(
        name = "Animated Counter",
        contributor = "Eleazar Cole-Showers",
        route = Screen.Counter
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
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            item {
                NeumorphicDownHorizontalDivider()
            }

            items(items = animatedCCItems) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(AccUIEvent.Navigate(it.route)) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = it.primaryText, contributor = it.secondaryText)
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}