package com.naulian.composable.scc

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.EntryProviderScope
import com.naulian.anhance.Lorem
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableComponent
import com.naulian.composable.scc.component.AnimatedParticles
import com.naulian.composable.scc.component.CorneredBox
import com.naulian.composable.scc.component.DepthCard
import com.naulian.composable.scc.component.GlassCard
import com.naulian.composable.scc.component.GlassCardCode
import com.naulian.composable.scc.component.MovieTicket
import com.naulian.composable.scc.component.NeuMorphicDown
import com.naulian.composable.scc.component.NeuMorphicUP
import com.naulian.composable.scc.component.Receipt
import com.naulian.composable.scc.component.cafeReceiptCode
import com.naulian.composable.scc.component.corneredBoxCode
import com.naulian.composable.scc.component.depthCardCode
import com.naulian.composable.scc.component.gridBackground
import com.naulian.composable.scc.component.gridBackgroundCode
import com.naulian.composable.scc.component.neumorphicCode
import com.naulian.composable.scc.component.verticalTicketShapeCode
import com.naulian.modify.Gray
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun EntryProviderScope<Screen>.StaticCCScreen(backStack: SnapshotStateList<Screen>) {
    entry<Screen.StaticCC> {
        StaticCCScreenUI {
            when (it) {
                SccUIEvent.Back -> backStack.removeLastOrNull()
                is SccUIEvent.Navigate -> backStack.add(
                    Screen.ComposableScreen(it.id)
                )
            }
        }
    }
}

val sccItemList = listOf(
    ComposableComponent(
        id = "Neumorphism",
        name = "Neumorphism",
        contributor = "Naulian",
        previewComponent = { NeumorphismComponent(modifier = it) },
        demoComponent = {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NeuMorphicUP(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center,
                )

                NeuMorphicDown(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentPadding = 10.dp,
                    contentAlignment = Alignment.Center,
                )

                NeuMorphicUP(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = CircleShape,
                    contentAlignment = Alignment.Center,
                )

                NeuMorphicDown(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = CircleShape,
                    contentPadding = 10.dp,
                    contentAlignment = Alignment.Center,
                )
            }
        },
        sourceCode = neumorphicCode
    ),
    ComposableComponent(
        id = "Grid Background",
        name = "Grid Background",
        contributor = "Naulian",
        previewComponent = { GridBgComponent(modifier = it) },
        demoComponent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .gridBackground(
                        color = MaterialTheme.colorScheme.surface,
                        lineColor = MaterialTheme.colorScheme.surfaceDim,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                // Add Ellipsis for preventing overflow on some devices
                Text(
                    text = Lorem.short,
                    fontSize = 64.sp,
                    color = Gray,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        sourceCode = gridBackgroundCode
    ),
    ComposableComponent(
        id = "Cornered Box",
        name = "Cornered Box",
        contributor = "Naulian",
        previewComponent = { CorneredBoxComponent(modifier = it) },
        demoComponent = {
            CorneredBox(
                modifier = Modifier
                    .fillMaxWidth(),
                cornerColor = Gray,
                onClick = {},
                contentPadding = PaddingValues(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = Lorem.short, fontSize = 64.sp, color = Gray)
            }
        },
        sourceCode = corneredBoxCode
    ),
    ComposableComponent(
        id = "Movie Ticket",
        name = "Movie Ticket",
        contributor = "Prashant Panwar",
        previewComponent = { TicketComponent(modifier = it) },
        demoComponent = {
            MovieTicket(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        },
        sourceCode = verticalTicketShapeCode
    ),
    ComposableComponent(
        id = "Glass Card",
        name = "Glass Card",
        contributor = "Shree Bhargav R K",
        previewComponent = { GlassCardComponent(modifier = it) },
        demoComponent = {
            val infiniteTransition = rememberInfiniteTransition()
            val animatedGradient by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF141E30),
                                Color(0xFF243B55),
                                Color(0xFF141E30)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(
                                x = 1000f * sin(animatedGradient * Math.PI.toFloat()).absoluteValue,
                                y = 1000f * cos(animatedGradient * Math.PI.toFloat()).absoluteValue
                            )
                        )
                    )
                    .padding(20.dp)
            ) {

                AnimatedParticles(particleCount = 15)

                GlassCard(width = 340.dp, height = 140.dp) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            "Upcoming Events",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            "• Team Meeting - 10:00 AM",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                        Text(
                            "• Client Call - 2:00 PM",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                        Text(
                            "• Review - 5:30 PM",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        },
        sourceCode = GlassCardCode
    ),
    ComposableComponent(
        id = "Depth Card",
        name = "Depth Card",
        contributor = "Romit Sharma",
        previewComponent = { DepthCardComponent(modifier = it) },
        demoComponent = {
            val colors = listOf(
                Color(0xFFFFCEB1),
                Color(0xFFD6E5BD),
                Color(0xFFF9E1A8),
                Color(0xFFBCD8EC),
                Color(0xFFDCCCEC),
                Color(0xFFFFDAB4)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 40.dp)
            ) {
                DepthCard(colors[0], R.drawable.depth1, modifier = Modifier.weight(1f))
                DepthCard(colors[1], R.drawable.depth2, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(70.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                DepthCard(colors[2], R.drawable.depth3, modifier = Modifier.weight(1f))
                DepthCard(colors[3], R.drawable.depth4, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(70.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                DepthCard(colors[4], R.drawable.depth5, modifier = Modifier.weight(1f))
                DepthCard(colors[5], R.drawable.depth6, modifier = Modifier.weight(1f))
            }
        },
        sourceCode = depthCardCode
    ),
    ComposableComponent(
        id = "Cafe Receipt",
        name = "Cafe Receipt",
        contributor = "Prashant Panwar",
        previewComponent = { ReceiptComponent(modifier = it) },
        demoComponent = { Receipt() },
        sourceCode = cafeReceiptCode
    )
)