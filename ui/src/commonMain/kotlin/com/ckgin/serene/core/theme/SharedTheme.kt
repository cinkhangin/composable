package com.ckgin.serene.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ckgin.serene.SereneTheme as SharedSereneTheme

@Composable
fun SereneTheme(content: @Composable () -> Unit) {
    SharedSereneTheme(content = content)
}

@Composable
fun SerenePreview(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    SereneTheme {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            content = content
        )
    }
}
