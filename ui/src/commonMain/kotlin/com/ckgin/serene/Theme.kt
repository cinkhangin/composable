package com.ckgin.serene

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Ink = Color(0xFF17181C)
private val Paper = Color(0xFFF6F7F9)
private val Porcelain = Color(0xFFFFFFFF)
private val Cobalt = Color(0xFF3659D9)
private val Coral = Color(0xFFD94870)
private val Verdigris = Color(0xFF0A8F78)
private val DarkPaper = Color(0xFF111214)

private val DarkThemeColors = darkColorScheme(
    primary = Color(0xFF91A7FF),
    onPrimary = Color(0xFF10183A),
    primaryContainer = Color(0xFF29355F),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFF87A5),
    onSecondary = Color(0xFF3D0B1C),
    tertiary = Color(0xFF63D6BA),
    background = DarkPaper,
    onBackground = Color(0xFFF2F3F6),
    surface = Color(0xFF191A1D),
    onSurface = Color(0xFFF5F6F8),
    surfaceVariant = Color(0xFF24262B),
    onSurfaceVariant = Color(0xFFC7CAD2),
    outline = Color(0xFF747985)
)

private val LightThemeColors = lightColorScheme(
    primary = Cobalt,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3E8FF),
    onPrimaryContainer = Ink,
    secondary = Coral,
    onSecondary = Color.White,
    tertiary = Verdigris,
    background = Paper,
    onBackground = Ink,
    surface = Porcelain,
    onSurface = Ink,
    surfaceVariant = Color(0xFFEDF0F5),
    onSurfaceVariant = Color(0xFF5F6470),
    outline = Color(0xFFB7BBC5)
)

@Composable
fun SereneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
