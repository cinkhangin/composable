package com.naulian.composable.icc.scratchCard

import android.graphics.Typeface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScratchCard(
    modifier: Modifier = Modifier,
    autoRevealed : Boolean = true
) {
    val isRevealed = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .size(250.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, Color(0xFFbcbcbc))
    ) {
        if (isRevealed.value) {
            // Show revealed card with animation
            RevealedCard()
        } else {
            ScratchLayer(
                onReveal = {
                    if (autoRevealed) {
                        isRevealed.value = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RevealedCard() {
    // A simple "jump" using scale animation
    val scale = remember { androidx.compose.animation.core.Animatable(0.8f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = androidx.compose.animation.core.spring(
                dampingRatio = 0.4f,
                stiffness = 200f
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .scale(scale.value),
        contentAlignment = Alignment.Center
    ) {
        Text("🎉 You Won!", fontSize = 22.sp, color = Color.Black)
    }
}

@Composable
private fun ScratchLayer(
    scratchColor: Color = Color(0xff5da0dd),
    brushWidth: Float = 30f,
    onReveal: () -> Unit
) {
    val currentPathState = remember { mutableStateOf(Path()) }
    val movedOffsetState = remember { mutableStateOf<Offset?>(null) }
    val clearedArea = remember { mutableStateOf(0f) }
    val totalArea = remember { mutableStateOf(1f) }

    Box {
        val paint = Paint().asFrameworkPaint()

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .pointerInput(true) {
                    detectDragGestures { change, _ ->
                        movedOffsetState.value = change.position
                    }
                }
        ) {
            paint.apply {
                isAntiAlias = true
                textSize = 45f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            // Total overlay area
            totalArea.value = size.width * size.height

            // Draw initial scratch surface
            drawRoundRect(scratchColor, size = Size(size.width, size.height))

            movedOffsetState.value?.let {
                currentPathState.value.addOval(Rect(it, brushWidth))
                // Add cleared area (rough approximation)
                clearedArea.value += (Math.PI * brushWidth * brushWidth).toFloat()
            }

            // Reveal text behind scratch
            clipPath(path = currentPathState.value, clipOp = ClipOp.Intersect) {
                drawRoundRect(Color.White, size = Size(size.width, size.height))
                drawIntoCanvas {
                    val text = "You won"
                    val textWidth = paint.measureText(text)
                    val fm = paint.fontMetrics
                    val textHeight = fm.descent - fm.ascent
                    val x = center.x - textWidth / 2
                    val y = center.y + (textHeight / 2 - fm.descent)

                    it.nativeCanvas.drawText(text, x, y, paint)
                }
            }

            // Check if cleared > 50%
            if ((clearedArea.value / totalArea.value) > 0.85f) {
                onReveal()
            }
        }
    }
}


val scratchCardCode = """
    @Composable
    fun ScratchCard(
        modifier: Modifier = Modifier,
        autoRevealed : Boolean = true
    ) {
        val isRevealed = remember { mutableStateOf(false) }

        Card(
            modifier = modifier
                .size(250.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp),
            border = BorderStroke(1.dp, Color(0xFFbcbcbc))
        ) {
            if (isRevealed.value) {
                // Show revealed card with animation
                RevealedCard()
            } else {
                ScratchLayer(
                    onReveal = {
                        if (autoRevealed) {
                            isRevealed.value = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun RevealedCard() {
        // A simple "jump" using scale animation
        val scale = remember { androidx.compose.animation.core.Animatable(0.8f) }

        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = 0.4f,
                    stiffness = 200f
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scale.value),
            contentAlignment = Alignment.Center
        ) {
            Text("🎉 You Won!", fontSize = 22.sp, color = Color.Black)
        }
    }

    @Composable
    private fun ScratchLayer(
        scratchColor: Color = Color(0xff5da0dd),
        brushWidth: Float = 30f,
        onReveal: () -> Unit
    ) {
        val currentPathState = remember { mutableStateOf(Path()) }
        val movedOffsetState = remember { mutableStateOf<Offset?>(null) }
        val clearedArea = remember { mutableStateOf(0f) }
        val totalArea = remember { mutableStateOf(1f) }

        Box {
            val paint = Paint().asFrameworkPaint()

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .pointerInput(true) {
                        detectDragGestures { change, _ ->
                            movedOffsetState.value = change.position
                        }
                    }
            ) {
                paint.apply {
                    isAntiAlias = true
                    textSize = 45f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }

                // Total overlay area
                totalArea.value = size.width * size.height

                // Draw initial scratch surface
                drawRoundRect(scratchColor, size = Size(size.width, size.height))

                movedOffsetState.value?.let {
                    currentPathState.value.addOval(Rect(it, brushWidth))
                    // Add cleared area (rough approximation)
                    clearedArea.value += (Math.PI * brushWidth * brushWidth).toFloat()
                }

                // Reveal text behind scratch
                clipPath(path = currentPathState.value, clipOp = ClipOp.Intersect) {
                    drawRoundRect(Color.White, size = Size(size.width, size.height))
                    drawIntoCanvas {
                        val text = "You won"
                        val textWidth = paint.measureText(text)
                        val fm = paint.fontMetrics
                        val textHeight = fm.descent - fm.ascent
                        val x = center.x - textWidth / 2
                        val y = center.y + (textHeight / 2 - fm.descent)

                        it.nativeCanvas.drawText(text, x, y, paint)
                    }
                }

                // Check if cleared > 50%
                if ((clearedArea.value / totalArea.value) > 0.85f) {
                    onReveal()
                }
            }
        }
    }
""".trimIndent()