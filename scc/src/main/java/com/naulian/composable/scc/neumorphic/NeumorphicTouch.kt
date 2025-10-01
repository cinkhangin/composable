package com.naulian.composable.scc.neumorphic

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.neumorphic.neumorphicUp

@Composable
fun NeumorphicTouch(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    onTouch: (Boolean) -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    var touch by remember { mutableStateOf(false) }
    val shadowPadding by animateDpAsState(
        targetValue = if (touch) 4.dp else contentPadding,
        animationSpec = spring()
    )

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.first()
                        touch = change.pressed
                        onTouch(change.pressed)
                    }
                }
            }
            .neumorphicUp(
                shape = shape,
                shadowPadding = shadowPadding
            ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Preview
@Composable
private fun NeumorphicTouchPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(100.dp),
        ) {
            var touch by remember { mutableStateOf(false) }
            NeumorphicTouch(
                modifier = Modifier.size(200.dp),
                onTouch = { touch = it },
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (touch) "Touch" else "Not Touch")
            }
        }
    }
}