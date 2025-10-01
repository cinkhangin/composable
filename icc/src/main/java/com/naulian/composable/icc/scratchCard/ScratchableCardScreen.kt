package com.naulian.composable.icc.scratchCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.columnItem

@Composable
fun ScratchableCardScreen() {
    val navController = LocalNavController.current
    val code = remember { scratchCardCode }
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Scratchable Card",
                onBack = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScratchCard(modifier = Modifier.padding(20.dp), autoRevealed = true)

                CodeBlock(
                    source = code,
                    language = "kotlin"
                )
            }
        }
    }
}