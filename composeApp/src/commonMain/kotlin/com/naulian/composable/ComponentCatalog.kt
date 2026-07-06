package com.naulian.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SharedComponent(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val previewHeight: Dp = 220.dp,
    val preview: @Composable (Modifier) -> Unit,
    val demo: (@Composable () -> Unit)? = null
)

val componentCatalog = listOf(
    SharedComponent(
        id = "rating-stars",
        name = "Rating Stars",
        category = "Interactive Compose Component",
        description = "Animated rating control ported without Android vector drawables.",
        preview = { modifier ->
            var rating by remember { mutableIntStateOf(3) }
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                RatingStars(rating = rating, onRatingChange = { rating = it })
            }
        }
    ),
    SharedComponent(
        id = "stackable-item",
        name = "Stackable Item",
        category = "Interactive Compose Component",
        description = "Layered stacked cards rebuilt with common layout and transforms.",
        previewHeight = 260.dp,
        preview = { modifier -> StackableItemDemo(modifier = modifier.padding(horizontal = 12.dp)) }
    ),
    SharedComponent(
        id = "better-carousel",
        name = "Better Carousel",
        category = "Interactive Compose Component",
        description = "Pager-based carousel using Compose Foundation APIs available to CMP.",
        previewHeight = 280.dp,
        preview = { modifier ->
            BetterCarousel(
                colors = listOf(
                    Color.White.copy(0.8f),
                    MaterialTheme.colorScheme.primary.copy(0.5f),
                    MaterialTheme.colorScheme.secondary.copy(0.5f),
                    MaterialTheme.colorScheme.tertiary.copy(0.5f)
                ),
                modifier = modifier,
                itemContent = { color ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color.copy(0.2f), RoundedCornerShape(16.dp))
                            .padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color, RoundedCornerShape(16.dp))
                        )
                    }
                }
            )
        }
    ),
    SharedComponent(
        id = "calendar-top-bar",
        name = "Calendar Top Bar",
        category = "Interactive Compose Component",
        description = "Selectable week strip ported to common code.",
        previewHeight = 120.dp,
        preview = { modifier ->
            var selectedDay by remember { mutableIntStateOf(3) }
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CalendarTopBar(selectedDay = selectedDay, onDaySelected = { selectedDay = it })
            }
        }
    ),
    SharedComponent(
        id = "heart-button",
        name = "Heart Button",
        category = "Interactive Compose Component",
        description = "Like button with burst particles using text glyphs instead of Android drawable resources.",
        preview = { modifier -> HeartButton(modifier = modifier) }
    ),
    SharedComponent(
        id = "glitch-text",
        name = "Glitch Text",
        category = "Animated Compose Component",
        description = "A layered animated text effect migrated from the Android app into shared Compose code.",
        preview = { modifier ->
            GlitchText(
                modifier = modifier.fillMaxWidth(),
                text = "GLITCH EFFECT"
            )
        }
    ),
    SharedComponent(
        id = "typing-text",
        name = "Typing Text",
        category = "Animated Compose Component",
        description = "A coroutine-driven text reveal that works in commonMain.",
        preview = { modifier ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center
            ) {
                TypingText(
                    text = "Compose once. Run on Android, iOS, desktop, and web.",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ),
    SharedComponent(
        id = "bubble-rise",
        name = "Rising Bubbles",
        category = "Canvas Animation",
        description = "Canvas particles using only Compose and Kotlin common APIs.",
        preview = { modifier ->
            BubbleRise(
                modifier = modifier,
                bubbleCount = 18,
                color = MaterialTheme.colorScheme.primary
            )
        }
    ),
    SharedComponent(
        id = "pulse-animation",
        name = "Pulse Animation",
        category = "Animated Compose Component",
        description = "Pulsing shape animation rebuilt with common Compose shapes.",
        preview = { modifier ->
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                PulseAnimation(
                    modifier = Modifier
                        .height(120.dp)
                        .aspectRatio(1f)
                )
            }
        }
    ),
    SharedComponent(
        id = "analog-clock",
        name = "Analog Clock",
        category = "Canvas Animation",
        description = "Clock drawing ported to common Canvas with animated hands.",
        preview = { modifier ->
            AnalogClock(modifier = modifier)
        }
    ),
    SharedComponent(
        id = "radar-effect",
        name = "Radar Effect",
        category = "Canvas Animation",
        description = "Radar sweep and blips using portable Canvas drawing.",
        preview = { modifier -> RadarAnimation(modifier = modifier) }
    ),
    SharedComponent(
        id = "vinyl-disk",
        name = "Vinyl Disk",
        category = "Canvas Animation",
        description = "Vinyl disk animation ported without Android blur/image resources.",
        preview = { modifier -> VinylDiskRotating(modifier = modifier) }
    ),
    SharedComponent(
        id = "neumorphism",
        name = "Neumorphism",
        category = "Static Compose Component",
        description = "Raised and inset-looking neumorphic surfaces using CMP-safe shadows.",
        preview = { modifier ->
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                NeumorphismDemo(modifier = Modifier.fillMaxWidth())
            }
        }
    ),
    SharedComponent(
        id = "grid-background",
        name = "Grid Background",
        category = "Static Compose Component",
        description = "Reusable clipped grid background modifier.",
        preview = { modifier ->
            Box(
                modifier = modifier
                    .gridBackground(
                        color = MaterialTheme.colorScheme.surface,
                        lineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = "GRID",
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ),
    SharedComponent(
        id = "cornered-box",
        name = "Cornered Box",
        category = "Static Compose Component",
        description = "Decorative corner-only border ported without nativeCanvas.",
        preview = { modifier ->
            CorneredBox(
                modifier = modifier,
                containerColor = MaterialTheme.colorScheme.surface,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Cornered", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }
    ),
    SharedComponent(
        id = "movie-ticket",
        name = "Movie Ticket",
        category = "Static Compose Component",
        description = "Ticket shape and barcode ported with generated CMP-safe poster art.",
        previewHeight = 360.dp,
        preview = { modifier -> MovieTicket(modifier = modifier.fillMaxWidth()) }
    ),
    SharedComponent(
        id = "raised-button",
        name = "Raised Button",
        category = "Interactive Compose Component",
        description = "A pressable depth button migrated without Android-specific input or resources.",
        preview = { modifier ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center
            ) {
                var presses by remember { mutableIntStateOf(0) }
                RaisedButton(
                    onClick = { presses++ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text("Pressed $presses times")
                }
            }
        }
    ),
    SharedComponent(
        id = "audio-player",
        name = "Audio Player",
        category = "Interactive Compose Component",
        description = "CMP-safe player UI shell; real playback needs per-platform audio adapters.",
        preview = { modifier -> AudioPlayerCard(modifier = modifier) }
    ),
    SharedComponent(
        id = "physics-button",
        name = "Physics Button",
        category = "Interactive Compose Component",
        description = "A springy button animation that compiles across every current target.",
        preview = { modifier ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center
            ) {
                var text by remember { mutableStateOf("Bounce me") }
                PhysicButton(
                    text = text,
                    onClick = { text = if (text == "Bounce me") "Nice bounce" else "Bounce me" },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ),
    SharedComponent(
        id = "glass-card",
        name = "Glass Card",
        category = "Static Compose Component",
        description = "The liquid glass card effect migrated to shared drawing code.",
        preview = { modifier ->
            GlassDemo(modifier = modifier)
        }
    ),
    SharedComponent(
        id = "depth-card",
        name = "Depth Card",
        category = "Static Compose Component",
        description = "Depth-card composition ported with generated card artwork.",
        preview = { modifier ->
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                DepthCardDemo(modifier = Modifier.fillMaxWidth())
            }
        }
    ),
    SharedComponent(
        id = "receipt",
        name = "Receipt",
        category = "Static Compose Component",
        description = "Wave receipt shape and receipt layout moved to commonMain.",
        previewHeight = 420.dp,
        preview = { modifier -> Receipt(modifier = modifier.padding(horizontal = 12.dp)) }
    ),
    SharedComponent(
        id = "step-progress",
        name = "Step Progress",
        category = "Interactive Compose Component",
        description = "The progress stepper rebuilt as common code.",
        previewHeight = 300.dp,
        preview = { modifier ->
            StepProgressDemo(
                modifier = modifier.padding(4.dp)
            )
        }
    )
)
