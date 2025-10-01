package com.naulian.composable.icc.stackable_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.stackingEffect(
    state: LazyListState,
    index: Int,
): Modifier {
    val isCurrentItem by remember {
        derivedStateOf { index == state.firstVisibleItemIndex }
    }

    val isUnderStack by remember {
        derivedStateOf { index < state.firstVisibleItemIndex }
    }

    val offset by remember {
        derivedStateOf {
            if (isCurrentItem) {
                state.firstVisibleItemScrollOffset.toFloat()
            } else {
                0f
            }
        }
    }

    val scale by remember {
        derivedStateOf {
            val progress = state.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
                offset / it.size.toFloat()
            } ?: 0f

            when {
                isUnderStack -> 0f
                isCurrentItem -> 1f - (progress * 0.2f)
                else -> 1f
            }
        }
    }

    return graphicsLayer {
        translationY = offset
        scaleX = scale
        scaleY = scale
    }
}

@Composable
fun StackableItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = color.copy(0.2f),
                shape = RoundedCornerShape(10)
            ).padding(8.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = color,
                    shape = RoundedCornerShape(10)
                )
        )
    }
}

val parallaxCardStackCode = """
@Composable
fun Modifier.stackingEffect(
    state: LazyListState,
    index: Int,
): Modifier {
    val isCurrentItem by remember {
        derivedStateOf { index == state.firstVisibleItemIndex }
    }

    val isUnderStack by remember {
        derivedStateOf { index < state.firstVisibleItemIndex }
    }

    val offset by remember {
        derivedStateOf {
            if (isCurrentItem) {
                state.firstVisibleItemScrollOffset.toFloat()
            } else {
                0f
            }
        }
    }

    val scale by remember {
        derivedStateOf {
            val progress = state.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
                offset / it.size.toFloat()
            } ?: 0f

            when {
                isUnderStack -> 0f
                isCurrentItem -> 1f - (progress * 0.1f)
                else -> 1f
            }
        }
    }
}
""".trimIndent()