package com.naulian.composable.component.icc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.R
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.component.defaultContainerColor
import com.naulian.composable.core.component.defaultShape
import com.naulian.composable.core.component.defaultSurfaceColor
import com.naulian.composable.core.theme.ComposablePreview
import com.naulian.modify.HugeIcons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun EmptyComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {

    }
}

@Preview
@Composable
private fun EmptyComponentPreview() {
    ComposablePreview {
        EmptyComponent(
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
            delay(1000)
            rating = if (rating == 0) 2 else 0
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
                    tint = if (index < animatedRating) defaultSurfaceColor.copy(0.6f)
                    else defaultSurfaceColor
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

@Composable
fun StackableItemComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        val scrollState = rememberLazyListState()
        val colors = listOf(
            Color.White.copy(0.6f),
            MaterialTheme.colorScheme.primary.copy(0.5f),
            Color.White.copy(0.6f),
            MaterialTheme.colorScheme.primary.copy(0.5f),
            Color.White.copy(0.6f),
            MaterialTheme.colorScheme.primary.copy(0.5f),
            Color.White.copy(0.6f),
            MaterialTheme.colorScheme.primary.copy(0.5f),
        )

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000) // Wait for 2 seconds
                scrollState.animateScrollBy(
                    value = 200f, // Scroll down by 200 pixels
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                )
                delay(1000)
                scrollState.animateScrollBy(
                    value = -200f, // Scroll up by 200 pixels
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(-(4).dp),
        ) {

            itemsIndexed(
                items = colors
            ) { index, color ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3f / 1f)
                        .stackingEffect(scrollState, index)
                        .background(
                            color = color,
                            shape = defaultShape
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun StackableItemComponentPreview() {
    ComposablePreview {
        StackableItemComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun BetterCarouselComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        val pagerState = rememberPagerState { 4 }
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000) // Wait for 2 seconds
                pagerState.animateScrollToPage(
                    page = 1, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
                delay(1000)
                pagerState.animateScrollToPage(
                    page = 2, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
                delay(1000)
                pagerState.animateScrollToPage(
                    page = 3, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
                delay(1000)
                pagerState.animateScrollToPage(
                    page = 2, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
                delay(1000)
                pagerState.animateScrollToPage(
                    page = 1, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
                delay(1000)
                pagerState.animateScrollToPage(
                    page = 0, // Scroll down by 200 pixels
                    animationSpec = tween(durationMillis = 1000)
                )
            }
        }
        BetterCarousel(
            pagerState = pagerState,
            colors = listOf(
                defaultSurfaceColor.copy(0.6f),
                MaterialTheme.colorScheme.primary.copy(0.5f),
                defaultSurfaceColor.copy(0.6f),
                MaterialTheme.colorScheme.primary.copy(0.5f),
            ),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            pageSpacing = 2.dp,
            itemContent = {
                Box(
                    modifier = Modifier
                        .background(
                            color = it.copy(0.2f),
                            shape = defaultShape
                        )
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = it,
                                shape = defaultShape
                            )
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun BetterCarouselComponentPreview() {
    ComposablePreview {
        BetterCarouselComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}


@Composable
fun StepsComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        var targetScale by remember { mutableStateOf(1f) }

        LaunchedEffect(targetScale) {
            delay(1000)
            targetScale = 1f - targetScale
        }

        val scale by animateFloatAsState(
            targetValue = targetScale,
            animationSpec = tween(
                durationMillis = 2000
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = defaultSurfaceColor,
                    shape = CircleShape
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(HugeIcons.Done),
                contentDescription = "Check",
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale),
                tint = defaultContainerColor.copy(0.5f)
            )
        }
    }
}

@Preview
@Composable
private fun StepsComponentPreview() {
    ComposablePreview {
        StepsComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun CalendarBarComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier, contentPadding = PaddingValues(0.dp)) {
        val scrollState = rememberLazyListState()

        LaunchedEffect(Unit) {
            while (true) {
                delay(500)
                scrollState.animateScrollBy(
                    value = 500f,
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                )
                delay(500)
                scrollState.animateScrollBy(
                    value = -500f,
                    animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(
                items = listOf(
                    "Sun" to 4,
                    "Mon" to 5,
                    "Tue" to 6,
                    "Wed" to 7,
                    "Thu" to 8,
                    "Fri" to 9,
                    "Sat" to 10,
                )
            ) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f / 2f)
                        .background(
                            color = defaultSurfaceColor,
                            shape = CircleShape
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = item.first)
                    Text(
                        text = String.format(Locale.getDefault(), "%02d", item.second),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarBarComponentPreview() {
    ComposablePreview {
        CalendarBarComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun RaisedButtonComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {

        var isPressed by remember { mutableStateOf(false) }

        LaunchedEffect(isPressed) {
            delay(1000)
            isPressed = !isPressed
        }

        RaisedToggleButton(
            modifier = Modifier,
            isPressed = isPressed,
            onPressed = { isPressed = it },
            color = defaultSurfaceColor.copy(0.7f),
            colorDark = defaultSurfaceColor.copy(0.4f),
            shape = RoundedCornerShape(30),
            height = 56.dp
        ) {
            Text(text = "")
        }
    }
}

@Preview
@Composable
private fun RaisedButtonComponentPreview() {
    ComposablePreview {
        RaisedButtonComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun PhysicsButtonComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        var pressed by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (pressed) 0.85f else 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            label = "PhysicsButtonScale"
        )

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                ),
            interactionSource = remember { MutableInteractionSource() },
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = defaultSurfaceColor
            )
        ) {
            Text("")
        }

        LaunchedEffect(Unit) {
            while (true) {
                pressed = true
                delay(150)
                pressed = false
                delay(1000)
            }
        }
    }
}

@Preview
@Composable
private fun PhysicsButtonComponentPreview() {
    ComposablePreview {
        PhysicsButtonComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun AudioPlayerComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        var target by remember { mutableFloatStateOf(0f) }

        LaunchedEffect(Unit) {
            target = 1f
        }

        val progress by animateFloatAsState(
            targetValue = target,
            animationSpec = if (target == 1f) tween(
                durationMillis = 7000,
                easing = LinearEasing
            ) else tween(
                durationMillis = 1000
            ),
            label = "progress",
            finishedListener = {
                target = 1f - target
            }
        )

        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = { progress },
            trackColor = defaultSurfaceColor,
            color = defaultSurfaceColor.copy(0.7f)
        )

        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(HugeIcons.Play),
            contentDescription = null,
            tint = defaultSurfaceColor
        )
    }
}

@Preview
@Composable
private fun AudioPlayerComponentPreview() {
    ComposablePreview {
        AudioPlayerComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun HeartButtonComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        var isLiked by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
            targetValue = if (isLiked) 1.2f else 1f,
            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        )

        LaunchedEffect(Unit) {
            while (true) {
                delay(1200)
                isLiked = !isLiked
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {

            var targetAlpha by remember { mutableFloatStateOf(1f) }
            var targetProgress by remember { mutableFloatStateOf(0f) }

            val alpha by animateFloatAsState(
                targetValue = targetAlpha,
                animationSpec = if (targetAlpha == 1f) tween(1)
                else tween(800, easing = LinearEasing),
            )
            val progress by animateFloatAsState(
                targetValue = targetProgress,
                animationSpec = if (targetProgress == 0f) tween(1)
                else tween(800, easing = FastOutSlowInEasing),
                finishedListener = {
                    targetProgress = 0f
                }
            )

            // Burst heart state
            var burstHeart by remember { mutableStateOf(emptyList<BurstHeart>()) }
            LaunchedEffect(isLiked) {
                if (isLiked) {
                    burstHeart = List(5) {
                        BurstHeart(
                            angle = Random.nextFloat() * 360f,
                            distance = Random.nextInt(40, 120).toFloat()
                        )
                    }

                    targetProgress = 1f
                    targetAlpha = 0f
                }else{
                    targetProgress = 0f
                    targetAlpha = 1f
                }
            }

            if(isLiked) {
                burstHeart.forEach { heart ->
                    Icon(
                        painter = painterResource(id = R.drawable.heart_filled),
                        contentDescription = "Burst Heart",
                        tint = defaultSurfaceColor.copy(1f),
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    x = (heart.distance * progress * cos(Math.toRadians(heart.angle.toDouble()))).toInt(),
                                    y = (heart.distance * progress * sin(Math.toRadians(heart.angle.toDouble()))).toInt()
                                )
                            }
                            .alpha(alpha)
                            .size(20.dp)
                    )
                }
            }
        }

        // Main heart
        Icon(
            painter = painterResource(id = if (isLiked) R.drawable.heart_filled else R.drawable.heart_unfilled),
            contentDescription = "heart",
            tint = if (isLiked) defaultSurfaceColor.copy(0.7f) else defaultSurfaceColor,
            modifier = Modifier
                .scale(scale)
                .padding(8.dp)
                .size(48.dp)
        )
    }
}

@Preview
@Composable
private fun HeartButtonComponentPreview() {
    ComposablePreview {
        HeartButtonComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}
