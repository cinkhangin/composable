package com.naulian.composable.core.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.naulian.modify.Black
import com.naulian.modify.DarkGray
import com.naulian.modify.LightGray
import com.naulian.modify.White
import com.naulian.neumorphic.NeumorphicTheme
import com.naulian.neumorphic.darkNeumorphicColorScheme
import com.naulian.neumorphic.lightNeumorphicColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = White,
    onPrimaryContainer = White,
    primaryFixedDim = DarkGreen,

    background = DarkBackground,
    onBackground = White,

    secondary = DarkYellow,
    tertiary = DarkRed,

    surface = Color(0xFF111111),
    onSurface = LightGray,
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = White,
    onPrimaryContainer = Black,
    primaryFixedDim = DarkGreen,

    background = LightBackground,
    onBackground = DarkGray,

    secondary = Yellow,
    tertiary = Red,

    surface = White,
    onSurface = DarkGray,
)

@Composable
fun ComposableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val neumorphicColorScheme = when {
        darkTheme -> darkNeumorphicColorScheme()
        else -> lightNeumorphicColorScheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    NeumorphicTheme(
        neumorphicColorScheme = neumorphicColorScheme,
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ComposablePreview(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    ComposableTheme {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            content = content
        )
    }
}