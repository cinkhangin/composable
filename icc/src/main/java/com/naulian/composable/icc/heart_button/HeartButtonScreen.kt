package com.naulian.composable.icc.heart_button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
fun HeartButtonScreen() {
    val navController = LocalNavController.current
    HeartButtonScreenUI(onBack = { navController.navigateUp() })
}

@Composable
fun HeartButtonScreenUI(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "3D Button",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeartButton()

            CodeBlock(source = heartButtonCode, language = "kotlin")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HeartButtonPreview() {
    ComposableTheme {
        HeartButtonScreenUI(onBack = {})
    }
}