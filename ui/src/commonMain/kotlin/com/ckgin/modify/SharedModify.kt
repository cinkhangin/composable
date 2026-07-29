package com.ckgin.modify

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import serene.ui.generated.resources.Res
import serene.ui.generated.resources.ic_arrow_left
import serene.ui.generated.resources.ic_done
import serene.ui.generated.resources.ic_next
import serene.ui.generated.resources.ic_pause
import serene.ui.generated.resources.ic_play
import serene.ui.generated.resources.ic_previous

val White = Color.White
val Gray = Color(0xFF888888)
val DarkGray = Color(0xFF444444)

val TextStyle.SemiBold get() = copy(fontWeight = FontWeight.SemiBold)

fun Modifier.noRippleClick(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit
) = clickable(
    enabled = enabled,
    interactionSource = interactionSource,
    indication = null,
    onClick = onClick
)

@Composable
fun HorizontalDottedLine(
    modifier: Modifier = Modifier,
    color: Color = DividerDefaults.color,
    thickness: Dp = DividerDefaults.Thickness,
    dotLength: Float = 4f,
    dotSpacing: Float = 6f
) {
    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(dotLength, dotSpacing),
        phase = 0f
    )

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxWidth().height(thickness)) {
            drawLine(
                color = color,
                start = Offset(0f, thickness.toPx() / 2f),
                end = Offset(size.width, thickness.toPx() / 2f),
                strokeWidth = thickness.toPx(),
                pathEffect = pathEffect
            )
        }
    }
}

object HugeIcons {
    val Back get() = Res.drawable.ic_arrow_left
    val Done get() = Res.drawable.ic_done
    val Next get() = Res.drawable.ic_next
    val Pause get() = Res.drawable.ic_pause
    val Play get() = Res.drawable.ic_play
    val Previous get() = Res.drawable.ic_previous
}
