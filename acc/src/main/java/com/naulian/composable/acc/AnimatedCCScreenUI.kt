package com.naulian.composable.acc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.core.component.ComponentItemList
import com.naulian.composable.core.component.ComposableTopAppBar

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data class Navigate(val id: String) : AccUIEvent
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCCScreenUI(uiEvent: (AccUIEvent) -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Animated Components",
                onBack = { uiEvent(AccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        ComponentItemList(
            items = accItemList,
            onClickItem = { uiEvent(AccUIEvent.Navigate(it)) },
            modifier = Modifier.fillMaxSize()
                .padding(scaffoldPadding)
        )
    }
}