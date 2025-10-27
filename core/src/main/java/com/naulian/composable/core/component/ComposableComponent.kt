package com.naulian.composable.core.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ComposableComponent(
    val id : String = "",
    val name : String = "",
    val contributor : String = "",
    val sourceCode : String = "",
    val previewComponent: @Composable (Modifier) -> Unit = {},
    val demoComponent : @Composable ColumnScope.(Modifier) -> Unit = {},
    val enabledScroll : Boolean = true,
    val showSourceCode : Boolean = true
)