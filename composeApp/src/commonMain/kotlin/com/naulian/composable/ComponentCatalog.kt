package com.naulian.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
