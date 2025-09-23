package com.naulian.composable.scc.depthCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.scc.R

@Composable
fun DepthCardScreen() {
    val navController = LocalNavController.current

    DepthCardScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun DepthCardScreenUI(onBack: () -> Unit) {
    val code = remember { depthCardCode }

    val colors = listOf(
        Color(0xFFFFCEB1),
        Color(0xFFD6E5BD),
        Color(0xFFF9E1A8),
        Color(0xFFBCD8EC),
        Color(0xFFDCCCEC),
        Color(0xFFFFDAB4)
    )
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Depth Card",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(72.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 40.dp)
            ) {
                DepthCard(colors[0], R.drawable.depth1, modifier = Modifier.weight(1f))
                DepthCard(colors[1], R.drawable.depth2, modifier = Modifier.weight(1f))
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                DepthCard(colors[2], R.drawable.depth3, modifier = Modifier.weight(1f))
                DepthCard(colors[3], R.drawable.depth4, modifier = Modifier.weight(1f))
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                DepthCard(colors[4], R.drawable.depth5, modifier = Modifier.weight(1f))
                DepthCard(colors[5], R.drawable.depth6, modifier = Modifier.weight(1f))
            }
            CodeBlock(source = code, language = "kotlin")
        }
    }
}