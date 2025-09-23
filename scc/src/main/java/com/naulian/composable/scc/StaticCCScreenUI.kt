package com.naulian.composable.scc

import android.R.attr.name
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
import com.naulian.modify.columnItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface SccUIEvent {
    data object Back : SccUIEvent
    data class Navigate(val route : Screen) : SccUIEvent
}

private val sccItemList = listOf(
    StaticCCItem(
        name = "Neumorphism",
        contributor = "Naulian",
        route = Screen.Neumorphism
    ),
    StaticCCItem(
        name = "Grid Background",
        contributor = "Naulian",
        route = Screen.GridBackground
    ),
    StaticCCItem(
        name = "Cornered Box",
        contributor = "Naulian",
        route = Screen.CorneredBox
    ),
    StaticCCItem(
        name = "Movie Ticket",
        contributor = "Prashant Panwar",
        route = Screen.MovieTicket
    ),
    StaticCCItem(
        name = "Glass Card",
        contributor = "Shree Bhargav R K",
        route = Screen.GlassCard
    ),
    StaticCCItem(
        name = "Depth Card",
        contributor = "Romit Sharma",
        route = Screen.DepthCard
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
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            item {
                NeumorphicDownHorizontalDivider()
            }

            items(items = sccItemList) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.Navigate(it.route)) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = it.name, contributor = it.contributor)
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}

private data class StaticCCItem(
    val name: String,
    val contributor: String,
    val route: Screen
)