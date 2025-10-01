package com.naulian.composable.icc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.theme.ComposablePreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val defaultShape = RoundedCornerShape(10)

private val defaultBackground @Composable get() = MaterialTheme.colorScheme.primary.copy(0.2f)
private val defaultSurface @Composable get() = MaterialTheme.colorScheme.surface.copy(0.3f)


@Composable
fun GlassCardComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 0.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface
                        )
                    ),
                    shape = defaultShape
                )
                .background(
                    color = defaultSurface,
                    shape = defaultShape
                )
        )
    }
}

@Preview
@Composable
private fun GlassCardComponentPreview() {
    ComposablePreview {
        GlassCardComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun RatingComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        var rating by remember { mutableIntStateOf(0) }

        LaunchedEffect(rating) {
            delay(2000)
            rating = if(rating == 0) 2 else 0
        }

        var animatedRating by remember { mutableIntStateOf(rating) }
        val boxesScale = List(5) {
            remember { Animatable(if (rating <= it) 0.8f else 1f) }
        }
        var prevRating by remember { mutableIntStateOf(rating) }


        LaunchedEffect(rating) {
            val isDecreased = rating < prevRating
            val boxes = if (isDecreased) boxesScale.take(prevRating).drop(rating).reversed()
            else boxesScale.drop(prevRating).take(rating - prevRating)

            prevRating = rating
            boxes.forEachIndexed { index, animatable ->
                launch {
                    delay(index * 200L)
                    animatedRating = if (isDecreased) animatedRating - 1
                    else animatedRating + 1
                    animatable.animateTo(
                        targetValue = if (isDecreased) 0.8f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(5) { index ->
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .scale(boxesScale[index].value),
                    painter = painterResource(if (index < animatedRating) R.drawable.ic_star_filled else R.drawable.ic_star_outlined),
                    contentDescription = null,
                    tint = if (index < animatedRating) MaterialTheme.colorScheme.surface.copy(0.7f)
                        else MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Preview
@Composable
private fun RatingComponentPreview() {
    ComposablePreview {
        RatingComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}