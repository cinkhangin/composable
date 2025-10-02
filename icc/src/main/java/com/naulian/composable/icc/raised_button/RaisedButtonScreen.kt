package com.naulian.composable.icc.raised_button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme

@Composable
fun RaisedButtonScreen() {
    val navController = LocalNavController.current

    RaisedButtonScreenUI(onBack = { navController.navigateUp() })
}

@Composable
fun RaisedButtonScreenUI(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Raised Button",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                RaisedButton(
                    onClick = {},
                    modifier = Modifier,
                    shape = CircleShape,
                    height = 56.dp
                ) {
                    Text(text = "Click")
                }

                RaisedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                RaisedButton(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }

                RaisedButton(
                    modifier = Modifier,
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                RaisedToggleButton(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp
                ) {
                    Text(text = "Toggle")
                }

                RaisedToggleButton(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp
                ) {
                    Text(text = "Toggle")
                }
            }

            CodeBlock(source = raisedButtonCode, language = "Kotlin")
        }
    }
}

@Preview
@Composable
private fun RaisedButtonScreenUIPreview() {
    ComposableTheme {
        RaisedButtonScreenUI { }
    }
}