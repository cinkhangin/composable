package com.naulian.composable.scc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.component.ComponentItemList
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.ExperimentalModifyApi

sealed interface SccUIEvent {
    data object Back : SccUIEvent
    data class Navigate(val id: String) : SccUIEvent
}

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
        ComponentItemList(
            items = sccItemList,
            onClickItem = { uiEvent(SccUIEvent.Navigate(it)) },
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
        )
    }
}