package com.naulian.composable.icc.stackable_item


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi

@Composable
fun StackableItemScreen() {
    val navController = LocalNavController.current

    StackableItemScreenUI(
        onBack = { navController.navigateUp() }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun StackableItemScreenUI(onBack: () -> Unit = {}) {
    val code = remember { parallaxCardStackCode }
    val scrollState = rememberLazyListState()

    val colors  = listOf(
        Color.White.copy(0.8f),
        MaterialTheme.colorScheme.primary.copy(0.5f),
        MaterialTheme.colorScheme.secondary.copy(0.5f),
        MaterialTheme.colorScheme.tertiary.copy(0.5f),
    )

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Stacking Card",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy((-52).dp),
            contentPadding = PaddingValues(20.dp)
        ) {

            itemsIndexed(
                items = colors
            ) { index, color ->
                StackableItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .stackingEffect(scrollState, index),
                    color = color
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
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
private fun ParallaxCardStackScreenUIPreview() {
    ComposableTheme {
        StackableItemScreenUI()
    }
}
