package com.naulian.composable.acc.vinyl_disk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.columnItem

@Composable
fun VinylDiskScreen() {
    val navController = LocalNavController.current

    VinylDiskUI(
        onBack = navController::navigateUp
    )
}

@Composable
private fun VinylDiskUI(onBack: () -> Unit = {}) {
    val code = remember { vinylDiskCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Vinyl Disk",
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
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VinylDiskRotating(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F),
                    needleColors = listOf(
                        Color(0x99444444),
                        Color(0x22999999),
                        Color(0x99444444),
                        Color(0xFFEAD961),
                        Color(0x59444444),
                        Color(0x12999999),
                        Color(0x997CFF58),
                        Color(0x0D999999),
                    )
                )

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
private fun RatingStarScreenUIPreview() {
    ComposableTheme {
        VinylDiskUI()
    }
}