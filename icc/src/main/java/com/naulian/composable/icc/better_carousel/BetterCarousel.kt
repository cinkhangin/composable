package com.naulian.composable.icc.better_carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.naulian.composable.core.theme.ComposableTheme
import kotlin.math.absoluteValue

@Composable
fun BetterCarousel(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState { colors.size },
    itemContent: @Composable BoxScope.(Color) -> Unit = {},
    contentPadding : PaddingValues = PaddingValues(64.dp),
    pageSpacing : Dp = 6.dp,
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = contentPadding,
        pageSpacing = pageSpacing,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(3)
        ),
        modifier = modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            ).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
        ) {
            itemContent.invoke(this, colors[page])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarouselPreview() {
    ComposableTheme {

        val colors = listOf(
            Color.White.copy(0.8f),
            MaterialTheme.colorScheme.primary.copy(0.5f),
            MaterialTheme.colorScheme.secondary.copy(0.5f),
            MaterialTheme.colorScheme.tertiary.copy(0.5f),
        )

        BetterCarousel(
            colors = colors,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
            }
        )
    }
}

val betterCarouselCode = """
@Composable
fun BetterCarousel(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    itemContent: @Composable BoxScope.(Color) -> Unit = {},
) {
    val pagerState = rememberPagerState(pageCount = { colors.size })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp),
        pageSpacing = 16.dp,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(3)
        ),
        modifier = modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            ).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
        ) {
            itemContent.invoke(this, colors[page])
        }
    }
}
""".trimIndent()