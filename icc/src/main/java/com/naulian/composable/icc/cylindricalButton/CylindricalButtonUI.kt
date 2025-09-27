package com.naulian.composable.icc.cylindricalButton

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IndustrialSquareButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonSize: Int = 120,
    cylinderDepth: Int = 20
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animation for pressing down the cylinder
    val animatedDepth by animateIntAsState(
        targetValue = if (isPressed) cylinderDepth / 3 else cylinderDepth,
        animationSpec = tween(durationMillis = 150),
        label = "depth"
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (cylinderDepth * 2 / 3).dp else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset"
    )

    Box(
        modifier = modifier.size((buttonSize + cylinderDepth).dp),
        contentAlignment = Alignment.TopCenter
    ) {

        // Square Cylinder Side Wall (the visible depth/body of the button)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp, (buttonSize + animatedDepth).dp)
                .background(
                    color = Color(0xFF972e25),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        // Button Top Surface (the actual pressable square top)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp)
                .background(
                    color = Color(0xFFFF5757),
                    shape = RoundedCornerShape(8.dp)
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            if(tryAwaitRelease())
                            isPressed = false
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (buttonSize / 8).sp
            )
        }
    }
}

@Composable
fun IndustrialCylinderButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonSize: Int = 120,
    cylinderDepth: Int = 20
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animation for pressing down the cylinder
    val animatedDepth by animateIntAsState(
        targetValue = if (isPressed) cylinderDepth / 3 else cylinderDepth,
        animationSpec = tween(durationMillis = 150),
        label = "depth"
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (cylinderDepth * 2 / 3).dp else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset"
    )

    Box(
        modifier = modifier.size((buttonSize + cylinderDepth).dp),
        contentAlignment = Alignment.TopCenter
    ) {

        // Cylinder Side Wall (the visible depth/body of the button)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp, (buttonSize + animatedDepth).dp)
                .background(
                    color = Color(0xFF972e25),
                    shape = RoundedCornerShape(
                        topStart = (buttonSize / 2).dp,
                        topEnd = (buttonSize / 2).dp,
                        bottomStart = (buttonSize / 2).dp,
                        bottomEnd = (buttonSize / 2).dp
                    )
                )
        )

        // Button Top Surface (the actual pressable circular top)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp)
                .background(
                    color = Color(0xFFFF5757),
                    shape = CircleShape
                )
                // Inner shadow/bevel effect on the top surface
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.2f)
                        ),
                        radius = (buttonSize / 1.5).toFloat()
                    ),
                    shape = CircleShape
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            if(tryAwaitRelease()) isPressed = false
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (buttonSize / 8).sp
            )
        }
    }
}

@Composable
fun IndustrialCylinderButtonOneState(
    text: String,
    modifier: Modifier = Modifier,
    buttonSize: Int = 120,
    cylinderDepth: Int = 20
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animation for pressing down the cylinder
    val animatedDepth by animateIntAsState(
        targetValue = if (isPressed) cylinderDepth / 3 else cylinderDepth,
        animationSpec = tween(durationMillis = 150),
        label = "depth"
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (cylinderDepth * 2 / 3).dp else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset"
    )

    Box(
        modifier = modifier.size((buttonSize + cylinderDepth).dp),
        contentAlignment = Alignment.TopCenter
    ) {

        // Cylinder Side Wall (the visible depth/body of the button)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp, (buttonSize + animatedDepth).dp)
                .background(
                    color = Color(0xFF972e25),
                    shape = RoundedCornerShape(
                        topStart = (buttonSize / 2).dp,
                        topEnd = (buttonSize / 2).dp,
                        bottomStart = (buttonSize / 2).dp,
                        bottomEnd = (buttonSize / 2).dp
                    )
                )
        )

        // Button Top Surface (the actual pressable circular top)
        Box(
            modifier = Modifier
                .offset(y = animatedButtonOffset)
                .size(buttonSize.dp)
                .clickable { isPressed = !isPressed }
                .background(
                    color = Color(0xFFFF5757),
                    shape = CircleShape
                )
                // Inner shadow/bevel effect on the top surface
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.2f)
                        ),
                        radius = (buttonSize / 1.5).toFloat()
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (buttonSize / 8).sp
            )
        }
    }
}

val cylindricalButtonCode by lazy {
    """
    @Composable
    fun IndustrialCylinderButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        buttonSize: Int = 120,
        cylinderDepth: Int = 20
    ) {
        var isPressed by remember { mutableStateOf(false) }

        // Animation for pressing down the cylinder
        val animatedDepth by animateIntAsState(
            targetValue = if (isPressed) cylinderDepth / 3 else cylinderDepth,
            animationSpec = tween(durationMillis = 100),
            label = "depth"
        )

        val animatedButtonOffset by animateDpAsState(
            targetValue = if (isPressed) (cylinderDepth * 2 / 3).dp else 0.dp,
            animationSpec = tween(durationMillis = 100),
            label = "buttonOffset"
        )

        Box(
            modifier = modifier.size((buttonSize + cylinderDepth).dp),
            contentAlignment = Alignment.TopCenter
        ) {

            // Cylinder Side Wall (the visible depth/body of the button)
            Box(
                modifier = Modifier
                    .offset(y = animatedButtonOffset)
                    .size(buttonSize.dp, (buttonSize + animatedDepth).dp)
                    .background(
                        color = Color(0xFF972e25),
                        shape = RoundedCornerShape(
                            topStart = (buttonSize / 2).dp,
                            topEnd = (buttonSize / 2).dp,
                            bottomStart = (buttonSize / 2).dp,
                            bottomEnd = (buttonSize / 2).dp
                        )
                    )
            )

            // Button Top Surface (the actual pressable circular top)
            Box(
                modifier = Modifier
                    .offset(y = animatedButtonOffset)
                    .size(buttonSize.dp)
                    .background(
                        color = Color(0xFFFF5757),
                        shape = CircleShape
                    )
                    // Inner shadow/bevel effect on the top surface
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f)
                            ),
                            radius = (buttonSize / 1.5).toFloat()
                        ),
                        shape = CircleShape
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                val released = tryAwaitRelease()
                                if (released) {
                                    onClick()
                                }
                                isPressed = false
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (buttonSize / 8).sp
                )
            }
        }
    }
""".trimIndent()
}