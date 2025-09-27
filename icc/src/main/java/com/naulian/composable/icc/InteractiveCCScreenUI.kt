package com.naulian.composable.icc

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
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface IccUIEvent {
    data object Back : IccUIEvent
    data class Navigate(val route: Screen) : IccUIEvent
}

private val iccItemList = listOf(
    InteractiveCCItem(
        name = "Rating Stars",
        contributor = "Naulian",
        route = Screen.RatingStars
    ),
    InteractiveCCItem(
        name = "Parallax Card Stack",
        contributor = "Aryan Jaiswal",
        route = Screen.ParallaxCardStack
    ),
    InteractiveCCItem(
        name = "Better Carousel",
        contributor = "Aryan Jaiswal",
        route = Screen.BetterCarousel
    ),
    InteractiveCCItem(
        name = "Steps Progress",
        contributor = "Aryan Singh",
        route = Screen.StepsProgress
    ),
    InteractiveCCItem(
        name = "Calender Top Bar",
        contributor = "Zain ul Abdin",
        route = Screen.CalenderTopBar
    ),
    InteractiveCCItem(
        name = "Cylindrical 3D Buttons",
        contributor = "Romit Sharma",
        route = Screen.CylindricalButtons
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(IccUIEvent.Navigate(it.route)) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = it.name, contributor = it.contributor)
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}

private data class InteractiveCCItem(
    val name: String,
    val contributor: String,
    val route: Screen
)