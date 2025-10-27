package com.naulian.composable.icc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.component.ComponentItemList
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.ExperimentalModifyApi

sealed interface IccUIEvent {
    data object Back : IccUIEvent
    data class Navigate(val id: String) : IccUIEvent
}

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InteractiveCCScreenUI(uiEvent: (IccUIEvent) -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Interactive Components",
                onBack = { uiEvent(IccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        ComponentItemList(
            items = iccItemList,
            onClickItem = { uiEvent(IccUIEvent.Navigate(it)) },
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        )
    }
}