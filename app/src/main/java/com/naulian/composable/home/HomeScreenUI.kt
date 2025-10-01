package com.naulian.composable.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.ItemUI
import com.naulian.composable.core.model.Item
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi


sealed interface HomeUIEvent {
    data class Navigate(val route: Screen) : HomeUIEvent
}

data class HomeUIState(
    val item: List<Item> = homeScreenList
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun HomeScreenUI(
    uiState: HomeUIState = HomeUIState(),
    uiEvent: (HomeUIEvent) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Composable",
                enableBack = false
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            state = listState
        ) {
            item {
                HorizontalDivider()
            }

            items(items = uiState.item) {
                ItemUI(
                    item = it,
                    onClick = { uiEvent(HomeUIEvent.Navigate(it.route)) }
                )
                HorizontalDivider()
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    ComposableTheme {
        HomeScreenUI { }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val homeScreenList = listOf(
    Item(
        primaryText = "Static Composable\nComponents",
        secondaryText = "Simple, ready-to-use UI pieces that keep your layout clean and consistent.",
        route = Screen.StaticCC,
        component = { StaticCC(modifier = it) }
    ),
    Item(
        primaryText = "Interactive Composable\nComponents",
        secondaryText = "UI elements that respond, react, and adapt when you click, type, or drag.",
        route = Screen.InteractiveCC,
        component = { InteractiveCCAnimation(modifier = it) }
    ),
    Item(
        primaryText = "Animated Composable\nComponents",
        secondaryText = "Smooth, playful motion effects that make your interface feel alive.",
        route = Screen.AnimatedCC,
        component = { AnimatedCCAnimation(modifier = it) }
    )
)
