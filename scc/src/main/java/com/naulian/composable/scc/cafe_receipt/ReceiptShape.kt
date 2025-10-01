
// WaveReceiptShape.kt
// A Jetpack Compose Shape that draws a receipt with sinusoid (wave-like cut) edges
// at the top and/or bottom, similar to typical printed receipt designs.
package com.naulian.composable.scc.cafe_receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min

/**
 * WaveReceiptShape — top and bottom sinusoidal edges that START and END on a crest.
 *
 * amplitude: vertical distance from crest to trough
 * cycles: number of full cycles across usable width (>= 1)
 * cornerRadius: left/right corner radius
 * samplingStepPx: sampling resolution in px
 */
class ReceiptShape(
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

@Preview
@Composable
fun ReceiptShapePreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .background(Color.White, shape = ReceiptShape(cycles = 20))
            )
        }
    }
}