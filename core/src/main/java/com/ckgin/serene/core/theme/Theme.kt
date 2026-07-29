package com.ckgin.serene.core.theme

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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ckgin.modify.Black
import com.ckgin.modify.DarkGray
import com.ckgin.modify.LightGray
import com.ckgin.modify.White
import com.ckgin.neumorphic.NeumorphicTheme
import com.ckgin.neumorphic.darkNeumorphicColorScheme
import com.ckgin.neumorphic.lightNeumorphicColorScheme

private val DarkGreenTheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = White,
    onPrimaryContainer = White,
    primaryFixedDim = DarkGreen,

    background = DarkBackground,
    onBackground = White,

    secondary = DarkYellow,
    tertiary = DarkRed,

    surface = DarkestGray,
    onSurface = LightGray,
    surfaceDim = Black
)

private val LightGreenTheme = lightColorScheme(
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
    surfaceDim = LightGray
)

@Composable
fun SereneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkGreenTheme
        else -> LightGreenTheme
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
fun SerenePreview(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    SereneTheme {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            content = content
        )
    }
}