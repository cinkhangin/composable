package com.naulian.composable.scc.grid_background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.anhance.Lorem
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.columnItem

@Composable
fun GridBackgroundScreen() {
    val navController = LocalNavController.current

    GridBackgroundScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
private fun GridBackgroundScreenUI(onBack: () -> Unit = {}) {
    val code = remember { gridBackgroundCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Grid Background",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .gridBackground(
                            color = MaterialTheme.colorScheme.surface,
                            lineColor = MaterialTheme.colorScheme.surfaceDim,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    // Add Ellipsis for preventing overflow on some devices
                    Text(text = Lorem.short, fontSize = 64.sp, color = Gray, overflow = TextOverflow.Ellipsis)
                }

                CodeBlock(
                    source = code,
                    language = "kotlin"
                )
            }
        }
    }
}

@Preview
@Composable
private fun GridBackgroundScreenUIPreview() {
    ComposableTheme {
        GridBackgroundScreenUI()
    }
}