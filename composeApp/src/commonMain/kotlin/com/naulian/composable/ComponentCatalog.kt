package com.naulian.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.acc.BubblesComponent
import com.naulian.composable.component.acc.ClockComponent
import com.naulian.composable.component.acc.GlitchEffectComponent
import com.naulian.composable.component.acc.PulseComponent
import com.naulian.composable.component.acc.RadarCode
import com.naulian.composable.component.acc.RadarEffectComponent
import com.naulian.composable.component.acc.TypingTextComponent
import com.naulian.composable.component.acc.VinylComponent
import com.naulian.composable.component.acc.bubbleRiseCode
import com.naulian.composable.component.acc.clockCode
import com.naulian.composable.component.acc.glitchCode
import com.naulian.composable.component.acc.pulseCode
import com.naulian.composable.component.acc.typingCode
import com.naulian.composable.component.acc.vinylDiskCode
import com.naulian.composable.component.icc.AudioPlayerComponent
import com.naulian.composable.component.icc.AudioPlayerDemo
import com.naulian.composable.component.icc.BetterCarouselComponent
import com.naulian.composable.component.icc.CalendarBarComponent
import com.naulian.composable.component.icc.HeartButtonComponent
import com.naulian.composable.component.icc.PhysicsButtonComponent
import com.naulian.composable.component.icc.RaisedButtonComponent
import com.naulian.composable.component.icc.RatingComponent
import com.naulian.composable.component.icc.StackableItemComponent
import com.naulian.composable.component.icc.StepsComponent
import com.naulian.composable.component.icc.audioPlayerCode
import com.naulian.composable.component.icc.betterCarouselCode
import com.naulian.composable.component.icc.calenderTopBarCode
import com.naulian.composable.component.icc.heartButtonCode
import com.naulian.composable.component.icc.physicsButtonCode
import com.naulian.composable.component.icc.raisedButtonCode
import com.naulian.composable.component.icc.ratingStarsCode
import com.naulian.composable.component.icc.stackableItemCode
import com.naulian.composable.component.scc.CorneredBoxComponent
import com.naulian.composable.component.scc.DepthCardComponent
import com.naulian.composable.component.scc.GlassCardCode
import com.naulian.composable.component.scc.GlassCardComponent
import com.naulian.composable.component.scc.GridBgComponent
import com.naulian.composable.component.scc.NeumorphismComponent
import com.naulian.composable.component.scc.ReceiptComponent
import com.naulian.composable.component.scc.TicketComponent
import com.naulian.composable.component.scc.cafeReceiptCode
import com.naulian.composable.component.scc.corneredBoxCode
import com.naulian.composable.component.scc.depthCardCode
import com.naulian.composable.component.scc.gridBackgroundCode
import com.naulian.composable.component.scc.neumorphicCode
import com.naulian.composable.component.scc.verticalTicketShapeCode

data class SharedComponent(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val previewHeight: Dp = 220.dp,
    val sourceCode: String,
    val preview: @Composable (Modifier) -> Unit,
    val demo: (@Composable () -> Unit)? = null
)

private fun originalComponent(
    id: String,
    name: String,
    category: String,
    contributor: String,
    sourceCode: String,
    previewHeight: Dp = 220.dp,
    demo: (@Composable () -> Unit)? = null,
    preview: @Composable (Modifier) -> Unit
) = SharedComponent(
    id = id,
    name = name,
    category = category,
    description = "Original Android component by $contributor, now rendered from commonMain.",
    previewHeight = previewHeight,
    sourceCode = sourceCode,
    demo = demo,
    preview = preview
)

val componentCatalog = listOf(
    originalComponent("typing-text", "Typing Text", "Animated Compose Component", "Shree Bhargav R K", typingCode) { TypingTextComponent(it) },
    originalComponent("pulse-animation", "Pulse Animation", "Animated Compose Component", "Shree Bhargav R K", pulseCode) { PulseComponent(it) },
    originalComponent("glitch-effect", "Glitch Effect", "Animated Compose Component", "Shree Bhargav R K", glitchCode) { GlitchEffectComponent(it) },
    originalComponent("analog-clock", "Analog Clock", "Canvas Animation", "Naulian", clockCode) { ClockComponent(it) },
    originalComponent("bubble-rise", "Bubble Rise", "Canvas Animation", "Eleazar Cole-Showers", bubbleRiseCode) { BubblesComponent(it) },
    originalComponent("radar-effect", "Radar Effect", "Canvas Animation", "Samarth", RadarCode) { RadarEffectComponent(it) },
    originalComponent("vinyl-disk", "Vinyl Disk", "Canvas Animation", "Donizete Vida", vinylDiskCode) { VinylComponent(it) },

    originalComponent("rating-stars", "Rating Stars", "Interactive Compose Component", "Naulian", ratingStarsCode) { RatingComponent(it) },
    originalComponent("stackable-item", "Stackable Item", "Interactive Compose Component", "Shree Bhargav R K", stackableItemCode, 260.dp) { StackableItemComponent(it) },
    originalComponent("better-carousel", "Better Carousel", "Interactive Compose Component", "Aryan Jaiswal", betterCarouselCode, 280.dp) { BetterCarouselComponent(it) },
    originalComponent("steps-progress", "Steps Progress", "Interactive Compose Component", "Aryan Singh", "// Source is pending in the original Android catalog.", 300.dp) { StepsComponent(it) },
    originalComponent("calender-top-bar", "Calender Top Bar", "Interactive Compose Component", "Zain ul Abdin", calenderTopBarCode) { CalendarBarComponent(it) },
    originalComponent("raised-button", "Raised Button", "Interactive Compose Component", "Romit Sharma", raisedButtonCode) { RaisedButtonComponent(it) },
    originalComponent("physics-button", "Physics Button", "Interactive Compose Component", "Eleazar Cole-Showers", physicsButtonCode) { PhysicsButtonComponent(it) },
    originalComponent(
        "audio-player",
        "Audio Player",
        "Interactive Compose Component",
        "Samarth",
        audioPlayerCode,
        demo = { AudioPlayerDemo() }
    ) { AudioPlayerComponent(it) },
    originalComponent("heart-button", "Heart Button", "Interactive Compose Component", "Mansi Kothari", heartButtonCode) { HeartButtonComponent(it) },

    originalComponent("neumorphism", "Neumorphism", "Static Compose Component", "Naulian", neumorphicCode) { NeumorphismComponent(it) },
    originalComponent("grid-background", "Grid Background", "Static Compose Component", "Naulian", gridBackgroundCode) { GridBgComponent(it) },
    originalComponent("cornered-box", "Cornered Box", "Static Compose Component", "Naulian", corneredBoxCode) { CorneredBoxComponent(it) },
    originalComponent("movie-ticket", "Movie Ticket", "Static Compose Component", "Prashant Panwar", verticalTicketShapeCode, 360.dp) { TicketComponent(it) },
    originalComponent("glass-card", "Glass Card", "Static Compose Component", "Shree Bhargav R K", GlassCardCode) { GlassCardComponent(it) },
    originalComponent("depth-card", "Depth Card", "Static Compose Component", "Romit Sharma", depthCardCode, 260.dp) { DepthCardComponent(it) },
    originalComponent("cafe-receipt", "Cafe Receipt", "Static Compose Component", "Prashant Panwar", cafeReceiptCode, 420.dp) { ReceiptComponent(it) }
)
