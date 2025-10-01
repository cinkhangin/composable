package com.naulian.composable.scc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.ItemUI
import com.naulian.composable.core.model.ComponentItem
import com.naulian.modify.ExperimentalModifyApi

sealed interface SccUIEvent {
    data object Back : SccUIEvent
    data class Navigate(val route: Screen) : SccUIEvent
}

private val sccItemList = listOf(
    ComponentItem(
        name = "Neumorphism",
        contributor = "Naulian",
        route = Screen.Neumorphism,
        component = { NeumorphismComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Grid Background",
        contributor = "Naulian",
        route = Screen.GridBackground,
        component = { GridBgComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Cornered Box",
        contributor = "Naulian",
        route = Screen.CorneredBox,
        component = { CorneredBoxComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Movie Ticket",
        contributor = "Prashant Panwar",
        route = Screen.MovieTicket,
        component = { NeumorphismComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Glass Card",
        contributor = "Shree Bhargav R K",
        route = Screen.GlassCard,
        component = { NeumorphismComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Depth Card",
        contributor = "Romit Sharma",
        route = Screen.DepthCard,
        component = { NeumorphismComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Cafe Receipt",
        contributor = "Prashant Panwar",
        route = Screen.CafeReceipt,
        component = { NeumorphismComponent(modifier = it) }
    ),
    ComponentItem(
        name = "Audio Player",
        contributor = "Samarth",
        route = Screen.AudioPlayer,
        component = { NeumorphismComponent(modifier = it) }
    )
)

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StaticCCScreenUI(
    uiEvent: (SccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Static Components",
                onBack = { uiEvent(SccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            item {
                HorizontalDivider()
            }

            items(items = sccItemList) {
                ItemUI(item = it, onClick = { uiEvent(SccUIEvent.Navigate(it.route)) })
                HorizontalDivider()
            }
        }
    }
}