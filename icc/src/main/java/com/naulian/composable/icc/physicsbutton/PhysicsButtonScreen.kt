package com.naulian.composable.icc.physicsbutton

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PhysicsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        onClick = { pressed = true; onClick() },
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
        interactionSource = remember { MutableInteractionSource() },
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text)
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(150)
            pressed = false
        }
    }
}