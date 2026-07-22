package com.ckgin.composable.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ckgin.composable.ComposableTheme as SharedComposableTheme

@Composable
fun ComposableTheme(content: @Composable () -> Unit) {
    SharedComposableTheme(content = content)
}

@Composable
fun ComposablePreview(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    ComposableTheme {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            content = content
        )
    }
}
