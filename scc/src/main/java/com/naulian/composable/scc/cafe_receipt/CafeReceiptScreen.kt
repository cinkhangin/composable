package com.naulian.composable.scc.cafe_receipt

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.scc.R
import com.naulian.modify.White
import com.naulian.neumorphic.ComposableTheme

@Composable
fun CafeReceiptScreen() {
    val navController = LocalNavController.current
    CafeReceiptScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun CafeReceiptScreenUI(
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Cafe Receipt",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->

        val cafeReceiptSource = remember { cafeReceipt }
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Receipt()

            CodeBlock(
                source = cafeReceiptSource,
                language = "kotlin"
            )
        }
    }
}

@Composable
fun Receipt(
    modifier: Modifier = Modifier
) {
    val receiptShape = ReceiptShape(amplitude = 20f)
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, receiptShape)
    ) {
        RoundedRectIcon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp, bottom = 8.dp),
            drawableResId = R.drawable.coffee
        )

        Text(
            "CASH RECEIPT",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.align(Alignment.CenterHorizontally),
            color = Color(0xFF404C65)
        )

        Spacer(modifier = Modifier.height(24.dp))

        DottedLineDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            dashLength = 4.dp,
            gap = 4.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        RowOfTwo(key = "Date", value = "01/01/2003")

        Spacer(modifier = Modifier.height(12.dp))

        RowOfTwo(key = "Manager", value = "Ben Parker")

        Spacer(modifier = Modifier.height(12.dp))

        RowOfTwo(key = "Cashier", value = "Peter Parker")

        Spacer(modifier = Modifier.height(24.dp))

        DottedLineDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            dashLength = 4.dp,
            gap = 4.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        RowOfTwo(key = "Coffee", value = "4.50 EUR")

        Spacer(modifier = Modifier.height(12.dp))

        RowOfTwo(key = "Carrot Cake", value = "8.50 EUR")

        Spacer(modifier = Modifier.height(24.dp))

        RowOfThree(first = "VAT", second = "4.5%", third = "0.52 EUR")

        Spacer(modifier = Modifier.height(12.dp))

        RowOfThree(first = "Total", second = "TTC", third = "13.52 EUR")

        Spacer(modifier = Modifier.height(24.dp))

        DottedLineDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            dashLength = 4.dp,
            gap = 4.dp
        )

        Text(
            modifier = modifier
                .align(Alignment.End)
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = "Total HT   13.52 EUR",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF404C65)
        )
    }
}

@Composable
fun RowOfTwo(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            key,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF404C65)
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF404C65)
        )
    }
}

@Composable
fun RowOfThree(
    modifier: Modifier = Modifier,
    first: String,
    second: String,
    third: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            first,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF404C65)
        )
        Text(
            second,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF404C65)
        )
        Text(
            third,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF404C65)
        )
    }
}

@Composable
fun RoundedRectIcon(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    backgroundColor: Color = Color(0x4D5A6B8F),
    drawableResId: Int,
    iconTint: Color? = Color.DarkGray, // optional tint
    onClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .size(48.dp)
            .background(backgroundColor)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawableResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp), // adjust size
            colorFilter = iconTint?.let { androidx.compose.ui.graphics.ColorFilter.tint(it) }
        )
    }
}

@Composable
fun DottedLineDivider(
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
fun ReceiptScreenPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            CafeReceiptScreenUI()
        }
    }
}

val cafeReceipt = """
    class WaveReceiptShape(
        private val amplitude: Float = 12f,
        private val cycles: Int = 10,
        private val cornerRadius: Float = 12f,
        private val samplingStepPx: Int = 1
    ) : Shape {

        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val w = size.width
            val h = size.height

            // Guard rails
            val cr = cornerRadius.coerceAtMost(min(w, h) / 2f)
            val usableLeft = cr
            val usableRight = w - cr
            // width available for the wave (excludes corner radii)
            val usableW = maxOf(0.0001f, usableRight - usableLeft)

            val k = maxOf(1, cycles) // number of cycles
            val wavelength = usableW / k

            // Calculate total number of sample points
            val pointsPerCycle = maxOf(3, (wavelength / samplingStepPx).toInt())
            // total samples across the whole usable width
            val totalPoints = pointsPerCycle * k

            // sinusoid
            fun topYAt(x: Float): Float {
                val t = (x - usableLeft) / usableW // normalized [0..1]
                // angle used by cosine so phase=0 at left crest and phase=2πk at right crest
                val phase = 2.0 * PI * k * t
                return (amplitude * (1 - cos(phase)) / 2).toFloat()
            }

            fun bottomYAt(x: Float): Float = h - topYAt(x) // top mirror

            val path = Path()

            // Start at top-left corner
            path.moveTo(0f, cr)
            if (cr > 0f) {
                path.quadraticTo(0f, 0f, usableLeft, 0f)
            } else {
                path.lineTo(usableLeft, 0f)
            }

            // Top wave
            for (i in 0..totalPoints) {
                val x = usableLeft + i * usableW / totalPoints
                path.lineTo(x, topYAt(x))
            }

            // Top-right corner
            if (cr > 0f) {
                path.quadraticTo(w, 0f, w, cr)
            } else {
                path.lineTo(w, cr)
            }

            // Right edge down
            path.lineTo(w, h - cr)

            // Bottom-right corner
            if (cr > 0f) {
                path.quadraticTo(w, h, w - cr, h)
            } else {
                path.lineTo(w - cr, h)
            }

            // --- Bottom wave (reverse) ---
            for (i in totalPoints downTo 0) {
                val x = usableLeft + i * usableW / totalPoints
                path.lineTo(x, bottomYAt(x))
            }

            // Bottom-left corner
            if (cr > 0f) {
                path.quadraticTo(0f, h, 0f, h - cr)
            } else {
                path.lineTo(0f, h - cr)
            }

            // Close path
            path.lineTo(0f, cr)
            path.close()

            return Outline.Generic(path)
        }
    }
    
    @Composable
    fun CafeReceiptScreenUI(
        onBack: () -> Unit = {}
    ) {
        Scaffold(
            topBar = {
                ComposableTopAppBar(
                    title = "Cafe Receipt",
                    onBack = onBack
                )
            }
        ) { scaffoldPadding ->

            val verticalShapeTicketSource = remember { verticalTicketShapeCode }
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                WaveReceipt()

                CodeBlock(
                    source = verticalShapeTicketSource,
                    language = "kotlin"
                )
            }
        }
    }

    @Composable
    fun WaveReceipt(
        modifier: Modifier = Modifier
    ) {
        val receiptShape = WaveReceiptShape(amplitude = 20f)
        Column(
            modifier = modifier
                .padding(32.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, receiptShape)
        ) {
            RoundedRectIcon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp, bottom = 8.dp),
                drawableResId = R.drawable.fork_spoon
            )

            Text(
                "CASH RECEIPT",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFF404C65)
            )

            Spacer(modifier = Modifier.height(24.dp))

            DottedLineDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                dashLength = 4.dp,
                gap = 4.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            RowOfTwo(key = "Date:", value = "01/01/1957")

            Spacer(modifier = Modifier.height(12.dp))

            RowOfTwo(key = "Store Manager:", value = "John Doe")

            Spacer(modifier = Modifier.height(12.dp))

            RowOfTwo(key = "Cashier:", value = "Peter Parker")

            Spacer(modifier = Modifier.height(24.dp))

            DottedLineDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                dashLength = 4.dp,
                gap = 4.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            RowOfTwo(key = "Coffee:", value = "4.50 EUR")

            Spacer(modifier = Modifier.height(12.dp))

            RowOfTwo(key = "Carrot Cake:", value = "8.50 EUR")

            Spacer(modifier = Modifier.height(24.dp))

            RowOfThree(first = "VAT:", second = "4.5%", third = "0.52 EUR")

            Spacer(modifier = Modifier.height(12.dp))

            RowOfThree(first = "Total:", second = "TTC", third = "13.52 EUR")

            Spacer(modifier = Modifier.height(24.dp))

            DottedLineDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                dashLength = 4.dp,
                gap = 4.dp
            )

            Text(
                modifier = modifier
                    .align(Alignment.End)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                text = "Total HT:   13.52 EUR",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF404C65)
            )
        }
    }

    @Composable
    fun RowOfTwo(
        modifier: Modifier = Modifier,
        key: String,
        value: String
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                key,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF404C65)
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF404C65)
            )
        }
    }

    @Composable
    fun RowOfThree(
        modifier: Modifier = Modifier,
        first: String,
        second: String,
        third: String,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                first,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF404C65)
            )
            Text(
                second,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF404C65)
            )
            Text(
                third,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF404C65)
            )
        }
    }

    @Composable
    fun RoundedRectIcon(
        modifier: Modifier = Modifier,
        cornerRadius: Dp = 16.dp,
        backgroundColor: Color = Color(0x4D5A6B8F),
        drawableResId: Int,
        iconTint: Color? = Color.DarkGray, // optional tint
        onClick: (() -> Unit)? = null
    ) {
        val shape = RoundedCornerShape(cornerRadius)

        Box(
            modifier = modifier
                .clip(shape)
                .size(48.dp)
                .background(backgroundColor)
                .then(
                    if (onClick != null) Modifier.clickable { onClick() } else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = drawableResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp), // adjust size
                colorFilter = iconTint?.let { androidx.compose.ui.graphics.ColorFilter.tint(it) }
            )
        }
    }

    @Composable
    fun DottedLineDivider(
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