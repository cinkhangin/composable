package com.naulian.composable.scc.depth_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme

@Composable
fun DepthCard(color: Color, image: Int, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(144.dp)
            .background(shape = RoundedCornerShape(16.dp), color = color),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(96.dp)
                .offset(y = (-24).dp)
                .height(144.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}

val depthCardCode by lazy {
    """
        @Composable
        fun DepthCard(color: Color, image: Int, modifier: Modifier) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(144.dp)
                    .background(shape = RoundedCornerShape(16.dp), color = color),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(96.dp)
                        .offset(y = (-24).dp)
                        .height(144.dp),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier,
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }
    """.trimIndent()
}

@Preview(showBackground = true)
@Composable
fun DepthCardPreview() {
    ComposableTheme {
        DepthCard(color = Color(0xFFFFCEB1), 2, modifier = Modifier)
    }
}