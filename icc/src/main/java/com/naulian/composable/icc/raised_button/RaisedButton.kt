package com.naulian.composable.icc.raised_button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RaisedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    height: Dp = 48.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    colorDark: Color = MaterialTheme.colorScheme.primaryFixedDim,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    content: @Composable RowScope.() -> Unit
) {

    var isPressed by remember { mutableStateOf(false) }

    val animatedDepth by animateDpAsState(
        targetValue = if (isPressed) elevation / 3 else elevation,
        animationSpec = tween(durationMillis = 150),
        label = "depth",
        finishedListener = {
            isPressed = false
        }
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (elevation * 2 / 3) else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset",
        finishedListener = {
            isPressed = false
        }
    )

    Box(
        modifier = modifier.height(
            height + elevation
        ),
        contentAlignment = Alignment.TopCenter
    ) {

        Box(
            modifier = modifier
                .offset(y = animatedButtonOffset)
                .widthIn(min = height)
                .height(height + animatedDepth)
                .background(color = colorDark, shape = shape)
        )

        ProvideContentColorTextStyle(
            contentColor = contentColor,
            textStyle = MaterialTheme.typography.labelLarge,
        ) {
            Row(
                modifier = modifier
                    .offset(y = animatedButtonOffset)
                    .widthIn(min = height)
                    .height(height)
                    .background(color = color, shape = shape)
                    .clickable { onClick(); isPressed = true },
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun RaisedToggleButton(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    height: Dp = 48.dp,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    content: @Composable RowScope.() -> Unit
) {

    var isPressed by remember { mutableStateOf(false) }

    val animatedDepth by animateDpAsState(
        targetValue = if (isPressed) elevation / 3 else elevation,
        animationSpec = tween(durationMillis = 150),
        label = "depth"
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (elevation * 2 / 3) else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset"
    )

    Box(
        modifier = modifier.height(height + elevation),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = modifier
                .offset(y = animatedButtonOffset)
                .widthIn(min = height)
                .height(height + animatedDepth)
                .background(
                    color = MaterialTheme.colorScheme.primaryFixedDim,
                    shape = shape
                )
        )

        ProvideContentColorTextStyle(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.labelLarge,
        ) {
            Row(
                modifier = modifier
                    .offset(y = animatedButtonOffset)
                    .widthIn(min = height)
                    .height(height)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = shape
                    )
                    .clip(shape)
                    .clickable { isPressed = !isPressed },
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content,
    )
}

val raisedButtonCode = """
@Composable
fun RaisedButton(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    height: Dp = 48.dp,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    content: @Composable RowScope.() -> Unit
) {

    var isPressed by remember { mutableStateOf(false) }

    val animatedDepth by animateDpAsState(
        targetValue = if (isPressed) elevation / 3 else elevation,
        animationSpec = tween(durationMillis = 150),
        label = "depth"
    )

    val animatedButtonOffset by animateDpAsState(
        targetValue = if (isPressed) (elevation * 2 / 3) else 0.dp,
        animationSpec = tween(durationMillis = 150),
        label = "buttonOffset"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {

        Box(
            modifier = modifier
                .offset(y = animatedButtonOffset)
                .widthIn(min = height)
                .height(height + animatedDepth)
                .background(
                    color = MaterialTheme.colorScheme.primaryFixedDim,
                    shape = shape
                )
        )

        ProvideContentColorTextStyle(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.labelLarge,
        ) {
            Row(
                modifier = modifier
                    .offset(y = animatedButtonOffset)
                    .widthIn(min = height)
                    .height(height)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = shape
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                if (tryAwaitRelease())
                                    isPressed = false
                            }
                        )
                    },
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}  
""".trimIndent()