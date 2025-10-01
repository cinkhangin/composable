package com.naulian.composable.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.SemiBold


sealed interface HomeUIEvent {
    data class Navigate(val route: Screen) : HomeUIEvent
}

data class HomeUIState(
    val menuItem : List<HomeScreenItem> = homeScreenList
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

            items(items = uiState.menuItem) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable{
                            uiEvent(HomeUIEvent.Navigate(it.route))
                        }
                ) {
                    val (texts, graphic) = createRefs()

                    it.component(
                        Modifier
                            .size(120.dp)
                            .constrainAs(graphic) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    Column(
                        modifier = Modifier
                            .constrainAs(texts) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(graphic.start)

                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                            .padding(end = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = it.primaryText,
                            style = MaterialTheme.typography.titleMedium.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = it.secondaryText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

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

data class HomeScreenItem(
    val primaryText: String,
    val secondaryText: String,
    val route: Screen,
    val component: @Composable (modifier: Modifier) -> Unit = {}
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val homeScreenList = listOf(
    HomeScreenItem(
        primaryText = "Static Composable\nComponents",
        secondaryText = "Simple, ready-to-use UI pieces that keep your layout clean and consistent.",
        route = Screen.StaticCC,
        component = { StaticCC(modifier = it) }
    ),
    HomeScreenItem(
        primaryText = "Interactive Composable\nComponents",
        secondaryText = "UI elements that respond, react, and adapt when you click, type, or drag.",
        route = Screen.InteractiveCC,
        component = { InteractiveCCAnimation(modifier = it) }
    ),
    HomeScreenItem(
        primaryText = "Animated Composable\nComponents",
        secondaryText = "Smooth, playful motion effects that make your interface feel alive.",
        route = Screen.AnimatedCC,
        component = { AnimatedCCAnimation(modifier = it) }
    )
)
