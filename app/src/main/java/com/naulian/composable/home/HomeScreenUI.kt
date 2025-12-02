package com.naulian.composable.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.R
import com.naulian.composable.acc.accItemList
import com.naulian.composable.core.Screen
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.composable.icc.iccItemList
import com.naulian.composable.scc.sccItemList
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.SemiBold


sealed interface HomeUIEvent {
    data class Navigate(val route: Screen) : HomeUIEvent
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun HomeScreenUI(
    uiEvent: (HomeUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { scaffoldPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Static Components")
            }

            items(count = sccItemList.size) {
                sccItemList[it].previewComponent(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            uiEvent(
                                HomeUIEvent.Navigate(
                                    Screen.Demo(sccItemList[it].id)
                                )
                            )
                        }
                )
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Interactive Components")
            }

            items(count = iccItemList.size) {
                iccItemList[it].previewComponent(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            uiEvent(
                                HomeUIEvent.Navigate(
                                    Screen.Demo(iccItemList[it].id)
                                )
                            )
                        }
                )
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text("Animated Components")
            }

            items(count = accItemList.size) {
                accItemList[it].previewComponent(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            uiEvent(
                                HomeUIEvent.Navigate(
                                    Screen.Demo(accItemList[it].id)
                                )
                            )
                        }
                )
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