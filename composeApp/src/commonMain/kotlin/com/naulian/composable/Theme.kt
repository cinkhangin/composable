package com.naulian.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Green = Color(0xFF00C853)
private val DarkGreen = Color(0xFF00A847)
private val Yellow = Color(0xFFFFD600)
private val DarkYellow = Color(0xFFFFAB00)
private val Red = Color(0xFFD50000)
private val DarkRed = Color(0xFF930000)
private val LightBackground = Color(0xFFF3F3F4)
private val DarkBackground = Color(0xFF101114)
private val DarkestGray = Color(0xFF1E1E27)

private val DarkThemeColors = darkColorScheme(
    primary = DarkGreen,
    onPrimary = Color.White,
    primaryContainer = DarkGreen.copy(alpha = 0.28f),
    onPrimaryContainer = Color.White,
    background = DarkBackground,
    onBackground = Color.White,
    secondary = DarkYellow,
    tertiary = DarkRed,
    surface = DarkestGray,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF292B31),
    onSurfaceVariant = Color(0xFFD7D7DD),
    outline = Color(0xFF6B6F78)
)

private val LightThemeColors = lightColorScheme(
    primary = Green,
    onPrimary = Color.White,
    primaryContainer = Green.copy(alpha = 0.18f),
    onPrimaryContainer = Color(0xFF102414),
    background = LightBackground,
    onBackground = Color(0xFF1F2420),
    secondary = Yellow,
    tertiary = Red,
    surface = Color.White,
    onSurface = Color(0xFF1F2420),
    surfaceVariant = Color(0xFFE9ECEA),
    onSurfaceVariant = Color(0xFF59635D),
    outline = Color(0xFF9BA39E)
)

@Composable
fun ComposableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
