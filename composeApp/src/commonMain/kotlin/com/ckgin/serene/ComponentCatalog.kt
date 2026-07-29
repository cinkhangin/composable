package com.ckgin.serene

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ckgin.serene.component.acc.BubblesComponent
import com.ckgin.serene.component.acc.ClockComponent
import com.ckgin.serene.component.acc.GlitchEffectComponent
import com.ckgin.serene.component.acc.PulseComponent
import com.ckgin.serene.component.acc.RadarCode
import com.ckgin.serene.component.acc.RadarEffectComponent
import com.ckgin.serene.component.acc.TypingTextComponent
import com.ckgin.serene.component.acc.VinylComponent
import com.ckgin.serene.component.acc.bubbleRiseCode
import com.ckgin.serene.component.acc.clockCode
import com.ckgin.serene.component.acc.glitchCode
import com.ckgin.serene.component.acc.pulseCode
import com.ckgin.serene.component.acc.typingCode
import com.ckgin.serene.component.acc.vinylDiskCode
import com.ckgin.serene.component.icc.AudioPlayerComponent
import com.ckgin.serene.demo.AudioPlayerDemo
import com.ckgin.serene.component.icc.BetterCarouselComponent
import com.ckgin.serene.component.icc.CalendarBarComponent
import com.ckgin.serene.component.icc.HeartButtonComponent
import com.ckgin.serene.component.icc.PhysicsButtonComponent
import com.ckgin.serene.component.icc.RaisedButtonComponent
import com.ckgin.serene.component.icc.RatingComponent
import com.ckgin.serene.component.icc.StackableItemComponent
import com.ckgin.serene.component.icc.StepsComponent
import com.ckgin.serene.component.icc.audioPlayerCode
import com.ckgin.serene.component.icc.betterCarouselCode
import com.ckgin.serene.component.icc.calenderTopBarCode
import com.ckgin.serene.component.icc.heartButtonCode
import com.ckgin.serene.component.icc.physicsButtonCode
import com.ckgin.serene.component.icc.raisedButtonCode
import com.ckgin.serene.component.icc.ratingStarsCode
import com.ckgin.serene.component.icc.stackableItemCode
import com.ckgin.serene.component.scc.CorneredBoxComponent
import com.ckgin.serene.component.scc.DepthCardComponent
import com.ckgin.serene.component.scc.GlassCardCode
import com.ckgin.serene.component.scc.GlassCardComponent
import com.ckgin.serene.component.scc.GridBgComponent
import com.ckgin.serene.component.scc.NeumorphismComponent
import com.ckgin.serene.component.scc.ReceiptComponent
import com.ckgin.serene.component.scc.TicketComponent
import com.ckgin.serene.component.scc.cafeReceiptCode
import com.ckgin.serene.component.scc.corneredBoxCode
import com.ckgin.serene.component.scc.depthCardCode
import com.ckgin.serene.component.scc.gridBackgroundCode
import com.ckgin.serene.component.scc.neumorphicCode
import com.ckgin.serene.component.scc.verticalTicketShapeCode

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
