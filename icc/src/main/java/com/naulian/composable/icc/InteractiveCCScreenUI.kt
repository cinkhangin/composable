package com.naulian.composable.icc

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
import com.naulian.modify.ExperimentalModifyApi

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
        component = { StepsComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Calender Top Bar",
        contributor = "Zain ul Abdin",
        route = Screen.CalenderTopBar,
        component = { EmptyComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Raised Button",
        contributor = "Romit Sharma",
        route = Screen.CylindricalButtons,
        component = { EmptyComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Physics Button",
        contributor = "Eleazar Cole-Showers",
        route = Screen.PhysicsButton,
        component = { EmptyComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Audio Player",
        contributor = "Samarth",
        route = Screen.AudioPlayer,
        component = { EmptyComponent(modifier = it) }
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
        LazyItemList(
            items = iccItemList,
            onClickItem = { uiEvent(IccUIEvent.Navigate(it.route)) },
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
        )
    }
}