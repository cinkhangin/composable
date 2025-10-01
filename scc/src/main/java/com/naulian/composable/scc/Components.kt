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
import com.naulian.composable.core.component.defaultContainerColor
import com.naulian.composable.core.component.defaultShape
import com.naulian.composable.core.component.defaultSurfaceColor
import com.naulian.composable.core.theme.ComposablePreview
import com.naulian.composable.scc.cafe_receipt.ReceiptShape
import com.naulian.composable.scc.cornered_box.CorneredBox
import com.naulian.composable.scc.grid_background.gridBackground
import com.naulian.composable.scc.ticket.Ticket
import com.naulian.modify.HorizontalDottedLine
import com.naulian.neumorphic.NeumorphicTheme
import com.naulian.neumorphic.neumorphicUp


@Composable
fun NeumorphismComponent(modifier: Modifier = Modifier) {
    BackgroundBox(modifier = modifier) {
        val shadowPadding = remember { 6.dp }
        NeumorphicTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .neumorphicUp(
                        shape = defaultShape,
                        shadowPadding = shadowPadding,
                        color = defaultSurfaceColor
                    )
            )
        }
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
                color = defaultContainerColor,
                lineColor = defaultSurfaceColor.copy(0.8f),
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
                color = defaultContainerColor,
                shape = defaultShape
            ),
        cornerColor = defaultSurfaceColor
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
            color = defaultSurfaceColor,
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
                        .background(defaultSurfaceColor.copy(0.6f), defaultShape)
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
                            defaultSurfaceColor.copy(1f),
                            Color.Transparent,
                            defaultSurfaceColor.copy(1f)
                        )
                    ),
                    shape = defaultShape
                )
                .background(
                    color = defaultSurfaceColor,
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
                    color = defaultSurfaceColor
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
                            color = Color.Black.copy(0.3f),
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                    )
                    .background(
                        color = defaultSurfaceColor.copy(0.7f),
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
                    color = defaultSurfaceColor.copy(0.6f)
                )
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            )
        ) {

            val textColor = Color.Black.copy(0.6f)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Coffee",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Text(
                    "$2.00",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Cake",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Text(
                    "$3.50",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }

            HorizontalDottedLine(
                color = textColor
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
                Text(
                    "$5.50",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
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