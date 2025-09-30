package com.naulian.composable.home

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

sealed interface HomeUIEvent {
    data class Navigate(val route: Screen) : HomeUIEvent
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val homeScreenList = listOf(
    HomeScreenItem(
        primaryText = "Static Composable\nComponents",
        secondaryText = """
            Essential building blocks with clean, consistent design.
            Ready-to-use UI elements for structure and layout.
            Simple, reliable, and visually balanced components.
        """.trimIndent(),
        route = Screen.StaticCC,
        component = { StaticCC(modifier = it) }
    ),
    HomeScreenItem(
        primaryText = "Interactive Composable\nComponents",
        secondaryText = """
            Engage users with dynamic, responsive elements.
            Click, type, or drag—designed for real interaction.
            Smart components that adapt to user input.
        """.trimIndent(),
        route = Screen.InteractiveCC,
        component = { InteractiveCCAnimation(modifier = it) }
    ),
    HomeScreenItem(
        primaryText = "Animated Composable\nComponents",
        secondaryText = """
            Bring your UI to life with smooth motion effects.
            Eye-catching transitions that enhance the experience.
            Animations that make your interface feel alive.
        """.trimIndent(),
        route = Screen.AnimatedCC,
        component = { AnimatedCCAnimation(modifier = it) }
    )
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
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                HorizontalDivider()
            }


            items(items = homeScreenList) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
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
                            style = MaterialTheme.typography.titleMedium,
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

private data class HomeScreenItem(
    val primaryText: String,
    val secondaryText: String,
    val route: Screen,
    val component: @Composable (modifier: Modifier) -> Unit = {}
)
