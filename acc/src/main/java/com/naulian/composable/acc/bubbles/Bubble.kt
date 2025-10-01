package com.naulian.composable.acc.bubbles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar

@Composable
fun BubbleRiseScreen() {
    val navController = LocalNavController.current

    BubbleRiseScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun BubbleRiseScreenUI(onBack: () -> Unit = {}) {
    val code = remember { bubbleRiseCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Bubble Rise",
                onBack = onBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp), // limit height for demo
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BubbleRise(modifier = Modifier.fillMaxSize())
                }
            }

            item {
                CodeBlock(
                    source = code,
                    language = "kotlin",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

val bubbleRiseCode = """
@Composable
fun BubbleRise(
    modifier: Modifier = Modifier,
    bubbleCount: Int = 12
) {
    val bubbles = remember {
        List(bubbleCount) {
            listOf(
                Random.nextFloat(),                     
                Random.nextInt(8, 24).toFloat(),      
                Random.nextInt(5000, 9000).toFloat(),    
                Random.nextLong(0, 4000).toFloat()       
            )
        }
    }

    Box(modifier = modifier) {
        bubbles.forEach { bubble ->
            RisingBubble(
                x = bubble[0],
                radius = bubble[1],
                durationMillis = bubble[2].toInt(),
                delayMillis = bubble[3].toInt()
            )
        }
    }
}

@Composable
fun RisingBubble(
    x: Float,
    radius: Float,
    durationMillis: Int,
    delayMillis: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bubbleRise")

    val yAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "yAnim"
    )

    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
                delayMillis = delayMillis
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "alphaAnim"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val xPos = this.size.width * x
        val yPos = this.size.height * yAnim
        drawCircle(
            color = Color(0xFF9C27B0).copy(alpha = alphaAnim),
            radius = radius,
            center = Offset(xPos, yPos)
        )
    }
}

""".trimIndent()