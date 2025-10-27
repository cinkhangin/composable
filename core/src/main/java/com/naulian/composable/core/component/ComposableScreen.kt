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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalComponents
import com.naulian.composable.core.LocalNavController
import com.naulian.modify.If

@Composable
fun ComposableScreen(componentId: String) {
    val navController = LocalNavController.current
    val components = LocalComponents.current

    ComposableScreenUI(
        component = components[componentId] ?: return,
        onBack = navController::navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ComposableScreenUI(
    component: ComposableComponent = ComposableComponent(),
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = component.name,
                onBack = onBack
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