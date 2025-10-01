package com.naulian.composable.icc

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.ItemUI
import com.naulian.composable.core.model.ComponentItem
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface IccUIEvent {
    data object Back : IccUIEvent
    data class Navigate(val route: Screen) : IccUIEvent
}

private val iccItemList = listOf(
    ComponentItem(
        name = "Rating Stars",
        contributor = "Naulian",
        route = Screen.RatingStars,
        component = { RatingComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Stackable Item",
        contributor = "Aryan Jaiswal",
        route = Screen.ParallaxCardStack,
        component = { StackableItemComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Better Carousel",
        contributor = "Aryan Jaiswal",
        route = Screen.BetterCarousel,
        component = { BetterCarouselComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Steps Progress",
        contributor = "Aryan Singh",
        route = Screen.StepsProgress,
        component = { GlassCardComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Calender Top Bar",
        contributor = "Zain ul Abdin",
        route = Screen.CalenderTopBar,
        component = { GlassCardComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Cylindrical 3D Buttons",
        contributor = "Romit Sharma",
        route = Screen.CylindricalButtons,
        component = { GlassCardComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Physics Button",
        contributor = "Eleazar Cole-Showers",
        route = Screen.PhysicsButton,
        component = { GlassCardComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Audio Player",
        contributor = "Samarth",
        route = Screen.AudioPlayer,
        component = { GlassCardComponent(modifier = it) }
    )
)

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InteractiveCCScreenUI(
    uiEvent: (IccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Interactive Components",
                onBack = { uiEvent(IccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            item {
                NeumorphicDownHorizontalDivider()
            }

            items(items = iccItemList) {
                ItemUI(it, onClick = { uiEvent(IccUIEvent.Navigate(it.route)) })
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}