package com.naulian.composable.icc.better_carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar

@Composable
fun BetterCarouselScreen() {
    val navController = LocalNavController.current

    BetterCarouselScreenUI(
        onBack = { navController.navigateUp() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BetterCarouselScreenUI(onBack: () -> Unit = {}) {
    val code = remember { betterCarouselCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Better Carousel",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
        ) {
            item {
                BetterCarousel(
                    colors = listOf(
                        Color.White.copy(0.8f),
                        MaterialTheme.colorScheme.primary.copy(0.5f),
                        MaterialTheme.colorScheme.secondary.copy(0.5f),
                        MaterialTheme.colorScheme.tertiary.copy(0.5f),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    itemContent = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = it.copy(0.2f),
                                    shape = RoundedCornerShape(10)
                                )
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = it,
                                        shape = RoundedCornerShape(10)
                                    )
                            )
                        }
                    },
                )
            }
            item {
                CodeBlock(
                    source = code,
                    language = "kotlin",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}