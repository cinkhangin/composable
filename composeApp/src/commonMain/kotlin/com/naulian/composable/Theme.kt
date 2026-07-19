package com.naulian.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Ink = Color(0xFF171717)
private val Paper = Color(0xFFFAFAF7)
private val Porcelain = Color(0xFFFFFFFF)
private val Mist = Color(0xFFE9ECE7)
private val Moss = Color(0xFF53685A)
private val Graphite = Color(0xFF2E3033)
private val DarkPaper = Color(0xFF151615)

private val DarkThemeColors = darkColorScheme(
    primary = Color(0xFFAFC1B2),
    onPrimary = Ink,
    primaryContainer = Color(0xFF25302A),
    onPrimaryContainer = Color.White,
    background = DarkPaper,
    onBackground = Color(0xFFF4F4EF),
    secondary = Color(0xFFC9CDBF),
    tertiary = Color(0xFFD8CFC1),
    surface = Color(0xFF1E201F),
    onSurface = Color(0xFFF6F6F0),
    surfaceVariant = Color(0xFF292B2A),
    onSurfaceVariant = Color(0xFFC8CBC3),
    outline = Color(0xFF747A70)
)

private val LightThemeColors = lightColorScheme(
    primary = Moss,
    onPrimary = Color.White,
    primaryContainer = Mist,
    onPrimaryContainer = Ink,
    background = Paper,
    onBackground = Ink,
    secondary = Graphite,
    tertiary = Color(0xFF9B8F82),
    surface = Porcelain,
    onSurface = Ink,
    surfaceVariant = Color(0xFFF1F2EE),
    onSurfaceVariant = Color(0xFF636760),
    outline = Color(0xFFBABEB6)
)

@Composable
fun ComposableTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
