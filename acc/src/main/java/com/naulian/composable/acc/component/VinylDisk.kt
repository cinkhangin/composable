package com.naulian.composable.acc.component

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.R
import com.naulian.composable.core.theme.ComposableTheme

private const val DEGREES = 360

@Composable
fun VinylDisk(
    modifier: Modifier,
    image: Painter = painterResource(R.drawable.clube_da_esquina),
    diskColor: Color = Color.Black,
    diskAngle: Float = 0f,
    lightingColor: Color = Color.Gray.copy(alpha = .4f),
    lightingAngle: Float = 30f,
    lightingAngles: List<Float> = listOf(
        30f,
        35f,
        30f,
        35f
    ),
    imageSize: Float = .35f,
    imageHoleSize: Float = .02f,
    needlePathCount: Int = 5,
    needleColors: List<Color> = listOf(
        Color(0x99444444),
        Color(0x22999999),
        Color(0x99444444),
        Color(0xFFEAD961),
        Color(0x59444444),
        Color(0x12999999),
        Color(0x997CFF58),
        Color(0x0D999999),
    ),
) {
    Canvas(
        modifier = modifier.rotate(diskAngle)
    ) {
        val radius = size.minDimension / 2

        Path().apply {
            addOval(Rect(center, size.minDimension * imageHoleSize)) //inner hole cut
            drawContext.transform.clipPath(this, ClipOp.Difference)
            addOval(Rect(center, radius)) //outer rounded border cut
            drawContext.transform.clipPath(this)
        }
        drawRect(diskColor) //disk color

        //some kinda of lighting into disk
        val lightingPaint = Paint().apply {
            color = lightingColor
            asFrameworkPaint().apply {
               maskFilter = BlurMaskFilter(100F, BlurMaskFilter.Blur.NORMAL)
            }
        }
        drawIntoCanvas {
            for (i in 0 until lightingAngles.size) {
                it.drawArc(
                    size.toRect(),
                    (i / 1f / lightingAngles.size) * DEGREES + lightingAngle,
                    lightingAngles[i],
                    true,
                    lightingPaint
                )
            }
        }

        // draw needle circular lines
        for (i in 0..needlePathCount) {
            // rotate to avoid gradient visual pattern
            rotate((i / 1f / needleColors.size) * DEGREES) {
                drawCircle(
                    Brush.sweepGradient(needleColors),
                    radius * (i / 1f / (needlePathCount + 1)),
                    style = Stroke(1F)
                )
            }
        }

        // draw center image and fix its offset position by
        // translating it when drawing
        val imageRadius = radius * imageSize
        val imagePath = Path().apply {
            addOval(Rect(center, imageRadius))
        }
        clipPath(imagePath) {
            translate(
                left = radius - imageRadius,
                top = radius - imageRadius
            ) {
                with(image) {
                    draw(size * imageSize)
                }
            }
        }
    }
}

@Preview
@Composable
private fun VinylDiskComponentPreview() {
    ComposableTheme {
        VinylDisk(
            Modifier.size(180.dp)
        )
    }
}

@Composable
fun VinylDiskRotating(
    modifier: Modifier = Modifier,
    diskColor: Color = Color.Black,
    needleColors: List<Color> = emptyList()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "disk")
    val diskAngle by infiniteTransition.animateFloat(
        0f,
        360f,
        infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val lightingAngle by infiniteTransition.animateFloat(
        360f,
        0f,
        infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    VinylDisk(
        modifier,
        diskColor = diskColor,
        needleColors = needleColors,
        diskAngle = diskAngle,
        lightingAngle = lightingAngle
    )
}

val vinylDiskCode by lazy {
    """
@Composable
fun VinylDisk(
    modifier: Modifier,
    image: Painter = painterResource(R.drawable.clube_da_esquina),
    diskColor: Color = Color.Black,
    diskAngle: Float = 0f,
    lightingColor: Color = Color.Gray.copy(alpha = .4f),
    lightingAngle: Float = 30f,
    lightingAngles: List<Float> = listOf(
        30f,
        35f,
        30f,
        35f
    ),
    imageSize: Float = .35f,
    imageHoleSize: Float = .02f,
    needlePathCount: Int = 5,
    needleColors: List<Color> = listOf(
        Color(0x99444444),
        Color(0x22999999),
        Color(0x99444444),
        Color(0xFFEAD961),
        Color(0x59444444),
        Color(0x12999999),
        Color(0x997CFF58),
        Color(0x0D999999),
    ),
) {
    Canvas(
        modifier = modifier.rotate(diskAngle)
    ) {
        val radius = size.minDimension / 2

        Path().apply {
            addOval(Rect(center, size.minDimension * imageHoleSize)) //inner hole cut
            drawContext.transform.clipPath(this, ClipOp.Difference)
            addOval(Rect(center, radius)) //outer rounded border cut
            drawContext.transform.clipPath(this)
        }
        drawRect(diskColor) //disk color

        //some kinda of lighting into disk
        val lightingPaint = Paint().apply {
            color = lightingColor
            asFrameworkPaint().apply {
                maskFilter = BlurMaskFilter(100F, BlurMaskFilter.Blur.NORMAL)
            }
        }
        drawIntoCanvas {
            for (i in 0 until lightingAngles.size) {
                it.drawArc(
                    size.toRect(),
                    (i / 1f / lightingAngles.size) * DEGREES + lightingAngle,
                    lightingAngles[i],
                    true,
                    lightingPaint
                )
            }
        }

        // draw needle circular lines
        for (i in 0..needlePathCount) {
            // rotate to avoid gradient visual pattern
            rotate((i / 1f / needleColors.size) * DEGREES) {
                drawCircle(
                    Brush.sweepGradient(needleColors),
                    radius * (i / 1f / (needlePathCount + 1)),
                    style = Stroke(1F)
                )
            }
        }

        // draw center image and fix its offset position by
        // translating it when drawing
        val imageRadius = radius * imageSize
        val imagePath = Path().apply {
            addOval(Rect(center, imageRadius))
        }
        clipPath(imagePath) {
            translate(
                left = radius - imageRadius,
                top = radius - imageRadius
            ) {
                with(image) {
                    draw(size * imageSize)
                }
            }
        }
    }
}
""".trimIndent()
}