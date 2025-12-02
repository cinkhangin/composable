package com.naulian.composable.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import com.naulian.composable.core.ListDemoScene
import com.naulian.composable.core.LocalBackButtonVisibility
import com.naulian.composable.core.LocalComponents
import com.naulian.composable.core.Screen
import com.naulian.modify.If

@Composable
fun EntryProviderScope<Screen>.DemoScreen(backStack: SnapshotStateList<Screen>) {
    entry<Screen.Demo>(
        metadata = ListDemoScene.demoPane()
    ) { key ->
        val components = LocalComponents.current
        DemoScreenUI(
            component = components[key.id] ?: components.values.first(),
            onBack = { backStack.removeLastOrNull() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoScreenUI(
    component: ComposableComponent = ComposableComponent(),
    onBack: () -> Unit = {}
) {
    val enableBack = LocalBackButtonVisibility.current

    Scaffold(
        topBar = {
            DemoTopAppBar(
                component = component,
                onBack = onBack,
                enableBack = enableBack
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .If(component.enabledScroll) {
                    verticalScroll(rememberScrollState())
                }
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            component.demoComponent(this, Modifier)

            if (component.showSourceCode) {
                Spacer(modifier = Modifier.height(20.dp))

                CodeBlock(
                    source = component.sourceCode,
                    codeName = component.name,
                    language = "kotlin"
                )
            }
        }
    }
}