package com.naulian.composable.scc.ticket

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.R
import com.naulian.composable.core.theme.ComposableTheme

@Composable
fun Ticket(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    topContent: @Composable BoxScope.() -> Unit = {},
    bottomContent: @Composable BoxScope.() -> Unit = {},
    dashLine : @Composable () -> Unit = {
        DottedDivider()
    },
    cutoutFraction: Float = 0.7f,
    cutoutRadius : Dp = 10.dp,
) {
    val ticketShape = VerticalTicketShape(
        cornerRadiusPercent = 10,
        cutoutRadius = cutoutRadius,
        cutoutHeightFraction = cutoutFraction
    )
    Column(
        modifier = modifier
            .background(color = color, shape = ticketShape)
            .clip(shape = ticketShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(cutoutFraction),
            content = topContent
        )

        dashLine()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            content = bottomContent
        )
    }
}

@Composable
fun MovieTicket(
    modifier: Modifier = Modifier,
    cutoutFraction: Float = 0.7f,
) {
    Ticket(
        modifier = modifier,
        cutoutFraction = cutoutFraction,
        topContent = {
            Image(
                painter = painterResource(R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        },
        bottomContent = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "Dec 25nd",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Time",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "6pm",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Seat",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "R4 5-7",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .padding(bottom = 12.dp),
                ) {
                    RandomBarcode(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun RandomBarcode(modifier: Modifier = Modifier) {
    val bars = List(40) { (4..16).random() } // random widths
    val gap = 4f

    Canvas(modifier = modifier) {
        val totalBarsWidth = bars.sumOf { it } + gap * (bars.size - 1)
        var x = (size.width - totalBarsWidth) / 2f // center offset

        bars.forEach { barWidth ->
            drawRect(
                color = Color.Black,
                topLeft = Offset(x, 0f),
                size = Size(barWidth.toFloat(), size.height)
            )
            x += barWidth + gap
        }
    }
}


@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    dashLength: Dp = 12.dp,
    gap: Dp = 8.dp,
    lineHeight: Dp = 1.dp,
    lineColor: Color = Color.Gray,
    strokeWidth: Dp = 2.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        val dashLength = dashLength.toPx()
        val gap = gap.toPx()
        var x = 0f
        val y = size.height / 2
        while (x < size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, y),
                end = Offset(x + dashLength, y),
                strokeWidth = strokeWidth.toPx()
            )
            x += dashLength + gap
        }
    }
}


@Preview
@Composable
fun TicketUIPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            MovieTicket(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(400.dp)
                    .fillMaxWidth()
            )
        }
    }
}

val verticalTicketShapeCode = """
class VerticalTicketShape(
    private val cornerRadius: Dp = 16.dp,
    private val cutoutRadius: Dp = 16.dp,
    /**
     * Fraction of the height where the cutout center will be placed.
     * Example: 0.5f = middle, 0.25f = upper quarter.
     */
    private val cutoutHeightFraction: Float = 0.80f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        with(density) {
            val cornerRadiusPx = cornerRadius.toPx()
            val cutoutRadiusPx = cutoutRadius.toPx()

            // Base ticket rectangle with rounded corners
            val ticketPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                    )
                )
            }

            // Calculate vertical center for cutouts
            val cutoutCenterY = size.height * cutoutHeightFraction

            // Cutout on the left edge
            val leftCutout = Path().apply {
                addOval(
                    Rect(
                        left = -cutoutRadiusPx,
                        top = cutoutCenterY - cutoutRadiusPx,
                        right = cutoutRadiusPx,
                        bottom = cutoutCenterY + cutoutRadiusPx
                    )
                )
            }

            // Cutout on the right edge
            val rightCutout = Path().apply {
                addOval(
                    Rect(
                        left = size.width - cutoutRadiusPx,
                        top = cutoutCenterY - cutoutRadiusPx,
                        right = size.width + cutoutRadiusPx,
                        bottom = cutoutCenterY + cutoutRadiusPx
                    )
                )
            }

            // Subtract cutouts from base shape
            val finalPath = Path().apply {
                op(ticketPath, leftCutout, PathOperation.Difference)
                op(this, rightCutout, PathOperation.Difference)
            }

            return Outline.Generic(finalPath)
        }
    }
}

@Composable
fun TicketUI(
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val ticketHeight = 400.dp
    val ticketWith = 320.dp
    val cutoutFraction = 0.7f
    val cutoutCenterHeight = ticketHeight * cutoutFraction
    val ticketShape = VerticalTicketShape(
        cornerRadius = 12.dp,
        cutoutRadius = 10.dp,
        cutoutHeightFraction = cutoutFraction
    )

    Box(
        modifier = modifier
            .size(width = ticketWith, height = ticketHeight)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = ticketShape
            )
            .clip(shape = ticketShape)
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cutoutCenterHeight)
                    .padding(4.dp)
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            
            DottedDivider()

            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "Dec 25nd",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Time",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "6pm",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Seat",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "R4 5-7",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(bottom = 12.dp),
            ) {
                RandomBarcode(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}

@Composable
fun RandomBarcode(modifier: Modifier = Modifier) {
    val bars = List(40) { (4..16).random() } // random widths
    val gap = 4f

    Canvas(modifier = modifier) {
        val totalBarsWidth = bars.sumOf { it } + gap * (bars.size - 1)
        var x = (size.width - totalBarsWidth) / 2f // center offset

        bars.forEach { barWidth ->
            drawRect(
                color = Color.Black,
                topLeft = Offset(x, 0f),
                size = Size(barWidth.toFloat(), size.height)
            )
            x += barWidth + gap
        }
    }
}

@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    dashLength: Dp = 12.dp,
    gap: Dp = 8.dp,
    lineHeight: Dp = 1.dp,
    lineColor: Color = Color.Gray,
    strokeWidth: Dp = 2.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        val dashLength = dashLength.toPx()
        val gap = gap.toPx()
        var x = 0f
        val y = size.height / 2
        while (x < size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, y),
                end = Offset(x + dashLength, y),
                strokeWidth = strokeWidth.toPx()
            )
            x += dashLength + gap
        }
    }
}
""".trimIndent()