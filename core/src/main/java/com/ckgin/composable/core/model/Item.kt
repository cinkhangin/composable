package com.ckgin.composable.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ckgin.composable.core.Screen

data class Item(
    val primaryText: String,
    val secondaryText: String,
    val route: Screen,
    val component: @Composable (modifier: Modifier) -> Unit = {}
)

@Suppress("FunctionName")
fun ComponentItem(
    name: String,
    contributor: String,
    route: Screen,
    component: @Composable (modifier: Modifier) -> Unit = {}
) = Item(
    primaryText = name,
    secondaryText = "Contributed by $contributor",
    route = route,
    component = component
)