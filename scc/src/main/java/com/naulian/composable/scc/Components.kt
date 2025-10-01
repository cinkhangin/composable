package com.naulian.composable.scc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.component.BackgroundBox
import com.naulian.composable.core.theme.ComposablePreview
import com.naulian.composable.scc.cafe_receipt.ReceiptShape
import com.naulian.composable.scc.cornered_box.CorneredBox
import com.naulian.composable.scc.grid_background.gridBackground
import com.naulian.composable.scc.ticket.Ticket
import com.naulian.modify.HorizontalDottedLine

private val defaultShape = RoundedCornerShape(10)

private val defaultBackground @Composable get() = MaterialTheme.colorScheme.primary.copy(0.2f)
private val defaultSurface @Composable get() = MaterialTheme.colorScheme.surface.copy(0.3f)

@Composable
fun NeumorphismComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        val shadowPadding = remember { 6.dp }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(0.2f),
                    shape = defaultShape
                )
                .innerShadow(
                    shape = defaultShape,
                    shadow = Shadow(
                        radius = shadowPadding,
                        color = MaterialTheme.colorScheme.surfaceBright,
                        offset = DpOffset(x = shadowPadding, y = shadowPadding)
                    )
                )
                .innerShadow(
                    shape = defaultShape,
                    shadow = Shadow(
                        radius = shadowPadding,
                        color = MaterialTheme.colorScheme.surfaceDim.copy(0.1f),
                        offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
                    )
                )
        )
    }
}

@Preview
@Composable
private fun NeumorphismComponentPreview() {
    ComposablePreview {
        NeumorphismComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun GridBgComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .gridBackground(
                color = defaultBackground,
                lineColor = MaterialTheme.colorScheme.surface,
                shape = defaultShape
            )
    )
}

@Preview
@Composable
private fun GridBgComponentPreview() {
    ComposablePreview {
        GridBgComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun CorneredBoxComponent(modifier: Modifier = Modifier) {
    CorneredBox(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = defaultBackground,
                shape = defaultShape
            )
    )
}

@Preview
@Composable
private fun CorneredBoxComponentPreview() {
    ComposablePreview {
        CorneredBoxComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun TicketComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        Ticket(
            modifier = Modifier.fillMaxSize(),
            cutoutFraction = 0.7f,
            cutoutRadius = 6.dp,
            color = MaterialTheme.colorScheme.surface.copy(0.5f),
            dashLine = {
                HorizontalDottedLine(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            topContent = {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface, defaultShape)
                        .clip(defaultShape)
                )
            },
            bottomContent = {}
        )
    }
}

@Preview
@Composable
private fun TicketComponentPreview() {
    ComposablePreview {
        TicketComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun GlassCardComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 0.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface
                        )
                    ),
                    shape = defaultShape
                )
                .background(
                    color = defaultSurface,
                    shape = defaultShape
                )
        )
    }
}

@Preview
@Composable
private fun GlassCardComponentPreview() {
    ComposablePreview {
        GlassCardComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun DepthCardComponent(modifier: Modifier = Modifier) {
    BackgroundBox(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(
                    shape = defaultShape,
                    color = defaultSurface
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .offset(y = (-20).dp)
                    .fillMaxHeight()
                    .dropShadow(
                        shape = defaultShape,
                        shadow = Shadow(
                            radius = 10.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(0.7f),
                        shape = defaultShape
                    ),
            ) {}
        }
    }
}

@Preview
@Composable
private fun DepthCardComponentPreview() {
    ComposablePreview {
        DepthCardComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}

@Composable
fun ReceiptComponent(modifier: Modifier = Modifier) {
    BackgroundBox(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = ReceiptShape(),
                    color = MaterialTheme.colorScheme.surface.copy(0.7f)
                )
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Coffee",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
                Text(
                    "$2.00",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Cake",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
                Text(
                    "$3.50",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }

            HorizontalDottedLine(
                color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
                Text(
                    "$5.50",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReceiptComponentPreview() {
    ComposablePreview {
        ReceiptComponent(
            modifier = Modifier
                .size(120.dp)
        )
    }
}