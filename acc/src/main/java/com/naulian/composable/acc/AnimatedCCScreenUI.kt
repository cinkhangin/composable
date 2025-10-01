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
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data class Navigate(val route: Screen) : AccUIEvent
}

private val animatedCCItems = listOf(
    AnimatedCCItem(
        name = "Typing Text",
        contributor = "Shree Bhargav R K",
        route = Screen.TypingText
    ),
    AnimatedCCItem(
        name = "Pulse Heart",
        contributor = "Shree Bhargav R K",
        route = Screen.PulseHeart
    ),
    AnimatedCCItem(
        name = "Glitch Effect",
        contributor = "Shree Bhargav R K",
        route = Screen.GlitchEffect
    ),
    AnimatedCCItem(
        name = "Analog Clock",
        contributor = "Naulian",
        route = Screen.Clock
    ),
    AnimatedCCItem(
        name = "Animated Counter",
        contributor = "Eleazar Cole-Showers",
        route = Screen.Counter
    ),
    AnimatedCCItem(
        name = "Bubble Rise",
        contributor = "Eleazar Cole-Showers",
        route = Screen.Bubbles
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
                    ListItemText(title = it.name, contributor = it.contributor)
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}

private data class AnimatedCCItem(
    val name: String,
    val contributor: String,
    val route: Screen
)