package com.naulian.composable.icc

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.createAttributionContext
import androidx.navigation3.runtime.EntryProviderScope
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableComponent
import com.naulian.composable.icc.component.AudioPlayer
import com.naulian.composable.icc.component.BetterCarousel
import com.naulian.composable.icc.component.CalenderTopBar
import com.naulian.composable.icc.component.HeartButton
import com.naulian.composable.icc.component.PhysicButton
import com.naulian.composable.icc.component.RaisedButton
import com.naulian.composable.icc.component.RaisedToggleButton
import com.naulian.composable.icc.component.RatingStars
import com.naulian.composable.icc.component.StackableItem
import com.naulian.composable.icc.component.StepProgressImpl
import com.naulian.composable.icc.component.audioPlayerCode
import com.naulian.composable.icc.component.betterCarouselCode
import com.naulian.composable.icc.component.calenderTopBarCode
import com.naulian.composable.icc.component.heartButtonCode
import com.naulian.composable.icc.component.physicsButtonCode
import com.naulian.composable.icc.component.raisedButtonCode
import com.naulian.composable.icc.component.ratingStarsCode
import com.naulian.composable.icc.component.stackableItemCode
import com.naulian.composable.icc.component.stackingEffect
import kotlinx.coroutines.delay

@Composable
fun EntryProviderScope<Screen>.InteractiveCCScreen(backStack: SnapshotStateList<Screen>) {
    entry<Screen.InteractiveCC> {
        InteractiveCCScreenUI {
            when (it) {
                IccUIEvent.Back -> backStack.removeLastOrNull()
                is IccUIEvent.Navigate -> backStack.add(
                   Screen.ComposableScreen(it.id)
                )
            }
        }
    }
}


val iccItemList = listOf(
    ComposableComponent(
        id = "Rating Stars",
        name = "Rating Stars",
        contributor = "Naulian",
        previewComponent = { RatingComponent(modifier = it) },
        demoComponent = {
            var ratingValue by remember { mutableIntStateOf(2) }

            RatingStars(
                modifier = Modifier.padding(20.dp),
                rating = ratingValue,
                ratedStarIcon = painterResource(R.drawable.ic_star_filled),
                unRatedStarIcon = painterResource(R.drawable.ic_star_outlined),
                onRatingChange = {
                    ratingValue = it
                },
                iconSize = 48.dp
            )
        },
        sourceCode = ratingStarsCode
    ),
    ComposableComponent(
        id = "Stackable Item",
        name = "Stackable Item",
        contributor = "Aryan Jaiswal",
        previewComponent = { StackableItemComponent(modifier = it) },
        demoComponent = {
            val scrollState = rememberLazyListState()

            val colors = listOf(
                Color.White.copy(0.8f),
                MaterialTheme.colorScheme.primary.copy(0.5f),
                MaterialTheme.colorScheme.secondary.copy(0.5f),
                MaterialTheme.colorScheme.tertiary.copy(0.5f),
            )

            LazyColumn(
                modifier = Modifier,
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy((-52).dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                itemsIndexed(
                    items = colors
                ) { index, color ->
                    StackableItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                            .stackingEffect(scrollState, index),
                        color = color
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    CodeBlock(
                        source = stackableItemCode,
                        language = "kotlin"
                    )
                }
            }
        },
        enabledScroll = false,
        showSourceCode = false
    ),
    ComposableComponent(
        id = "Better Carousel",
        name = "Better Carousel",
        contributor = "Aryan Jaiswal",
        previewComponent = { BetterCarouselComponent(modifier = it) },
        demoComponent = {
            BetterCarousel(
                colors = listOf(
                    Color.White.copy(0.8f),
                    MaterialTheme.colorScheme.primary.copy(0.5f),
                    MaterialTheme.colorScheme.secondary.copy(0.5f),
                    MaterialTheme.colorScheme.tertiary.copy(0.5f),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
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
                },
            )
        },
        sourceCode = betterCarouselCode
    ),
    ComposableComponent(
        id = "Steps Progress",
        name = "Steps Progress",
        contributor = "Aryan Singh",
        previewComponent = { StepsComponent(modifier = it) },
        demoComponent = {
            StepProgressImpl(it)
        },
        sourceCode = "" //todo add source code
    ),
    ComposableComponent(
        id = "Calender Top Bar",
        name = "Calender Top Bar",
        contributor = "Zain ul Abdin",
        previewComponent = { CalendarBarComponent(modifier = it) },
        demoComponent = {
            CalenderTopBar(
                onDateSelected = {
                    // do opp like filtering etc
                },
                onBack = {}
            )
        },
        sourceCode = calenderTopBarCode
    ),
    ComposableComponent(
        id = "Raised Button",
        name = "Raised Button",
        contributor = "Romit Sharma",
        previewComponent = { RaisedButtonComponent(modifier = it) },
        demoComponent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                RaisedButton(
                    onClick = {},
                    modifier = Modifier,
                    shape = RoundedCornerShape(28.dp),
                    height = 56.dp
                ) {
                    Text(text = "Click")
                }

                RaisedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button
                RaisedButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }

                RaisedButton(
                    modifier = Modifier,
                    height = 56.dp,
                    onClick = {},
                ) {
                    Text(text = "Click")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Large emergency-style button

                var isPressed by remember { mutableStateOf(false) }

                RaisedToggleButton(
                    isPressed = isPressed,
                    onPressed = {
                        isPressed = it
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp
                ) {
                    Text(text = "Toggle")
                }

                RaisedToggleButton(
                    isPressed = !isPressed,
                    onPressed = {
                        isPressed = !it
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    height = 56.dp
                ) {
                    Text(text = "Toggle")
                }
            }
        },
        sourceCode = raisedButtonCode
    ),
    ComposableComponent(
        id = "Physics Button",
        name = "Physics Button",
        contributor = "Eleazar Cole-Showers",
        previewComponent = { PhysicsButtonComponent(modifier = it) },
        demoComponent = {
            PhysicButton(
                text = "Press Me!",
                onClick = {}
            )
        },
        sourceCode = physicsButtonCode
    ),
    ComposableComponent(
        id = "Audio Player",
        name = "Audio Player",
        contributor = "Samarth",
        previewComponent = { AudioPlayerComponent(modifier = it) },
        demoComponent = {
            val context = LocalContext.current
            val audioAttributionContext: Context =
                if (Build.VERSION.SDK_INT >= 30) createAttributionContext(
                    context,
                    "audioPlayback"
                ) else context

            val player = remember {
                MediaPlayer.create(audioAttributionContext, R.raw.music)
            }

            var currentPosition by remember { mutableIntStateOf(0) }
            var isPlaying by remember { mutableStateOf(false) }

            LaunchedEffect(player) {
                while (true) {
                    delay(100)
                    currentPosition = player.currentPosition
                    isPlaying = player.isPlaying
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    player.stop()
                    player.release()
                }
            }


            AudioPlayer(
                isPlaying = isPlaying,
                progress = { currentPosition.toFloat() / player.duration },
                onClickPlay = { player.start() },
                onClickPause = { player.pause() }
            )
        },
        sourceCode = audioPlayerCode
    ),
    ComposableComponent(
        id = "Heart Button",
        name = "Heart Button",
        contributor = "Mansi Kothari",
        previewComponent = { HeartButtonComponent(modifier = it) },
        demoComponent = {
            HeartButton()
        },
        sourceCode = heartButtonCode
    )
)