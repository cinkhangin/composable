package com.naulian.composable.icc.component

import androidx.annotation.IntRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.icc.R
import com.naulian.modify.noRippleClick
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * var ratingValue by remember { mutableIntStateOf(0) }
 * RatingStars(
 *     modifier = Modifier.padding(20.dp),
 *     rating = ratingValue, //IntRange 0, 5
 *     ratedStarIcon = resourcePainter(R.drawable.ic_star_filled),
 *     unRatedStarIcon = resourcePainter(R.drawable.ic_star_outlined),
 *     onRatingChange = { ratingValue = it }
 * )
 *  * */
@Composable
fun RatingStars(
    @IntRange(0, 5)
    rating: Int,
    onRatingChange: (Int) -> Unit,
    unRatedStarIcon: Painter,
    ratedStarIcon: Painter,
    modifier: Modifier = Modifier,
    iconSize: Dp = 40.dp,
    itemSpacing: Dp = 8.dp,
    animOverlapDelay: Long = 200L,
    animationSpec: AnimationSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessLow
    )
) {

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
                delay(index * animOverlapDelay)
                animatedRating = if (isDecreased) animatedRating - 1
                else animatedRating + 1
                animatable.animateTo(
                    targetValue = if (isDecreased) 0.8f else 1f,
                    animationSpec = animationSpec
                )
            }
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        repeat(5) { index ->
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .scale(boxesScale[index].value)
                    .noRippleClick {
                        onRatingChange(index + 1)
                    },
                painter = if (index < animatedRating) ratedStarIcon else unRatedStarIcon,
                contentDescription = null,
                tint = if (index < animatedRating) Color.Unspecified else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview
@Composable
private fun RatingStarsPreview() {
    Box(modifier = Modifier.background(White)) {
        var ratingValue by remember { mutableIntStateOf(2) }

        RatingStars(
            modifier = Modifier.padding(20.dp),
            rating = ratingValue,
            ratedStarIcon = painterResource(R.drawable.ic_star_filled),
            unRatedStarIcon = painterResource(R.drawable.ic_star_outlined),
            onRatingChange = {
                ratingValue = it
            }
        )
    }
}

val ratingStarsCode by lazy {
    """
        @Composable
        fun RatingStars(
            @IntRange(0, 5)
            rating: Int,
            onRatingChange: (Int) -> Unit,
            unRatedStarIcon: Painter,
            ratedStarIcon: Painter,
            modifier: Modifier = Modifier,
            iconSize: Dp = 40.dp,
            itemSpacing: Dp = 8.dp,
            animOverlapDelay: Long = 200L,
            animationSpec: AnimationSpec<Float> = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) {
        
            var animatedRating by remember { mutableIntStateOf(rating) }
            val boxesScale = List(5) {
                remember { Animatable(if(rating <= it) 0.8f else 1f) }
            }
            var prevRating by remember { mutableIntStateOf(rating) }
        
        
            LaunchedEffect(rating) {
                val isDecreased = rating < prevRating
                val boxes = if (isDecreased) boxesScale.take(prevRating).drop(rating).reversed()
                else boxesScale.drop(prevRating).take(rating - prevRating)
        
                prevRating = rating
                boxes.forEachIndexed { index, animatable ->
                    launch {
                        delay(index * animOverlapDelay)
                        animatedRating = if (isDecreased) animatedRating - 1
                        else animatedRating + 1
                        animatable.animateTo(
                            targetValue = if (isDecreased) 0.8f else 1f,
                            animationSpec = animationSpec
                        )
                    }
                }
            }
        
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(itemSpacing)
            ) {
                repeat(5) { index ->
                    Icon(
                        modifier = Modifier
                            .size(iconSize)
                            .scale(boxesScale[index].value)
                            .noRippleClick {
                                onRatingChange(index + 1)
                            },
                        painter = if (index < animatedRating) ratedStarIcon else unRatedStarIcon,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    """.trimIndent()
}