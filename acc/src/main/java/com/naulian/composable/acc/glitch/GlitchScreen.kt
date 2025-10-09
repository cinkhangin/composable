package com.naulian.composable.acc.glitch


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme

@Composable
fun GlitchScreen() {
    val navController = LocalNavController.current

    GlitchScreenUI (
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun GlitchScreenUI(onBack: () -> Unit = {}) {
    val code = remember { animationCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Glitch Effect",
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
                GlitchText()
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


@Preview(showBackground = true)
@Composable
fun AnimationsInteractionsScreenUIPreview() {
    ComposableTheme {
        GlitchScreenUI()
    }
}


private val animationCode =
"""
@Composable
fun GlitchEffect(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(100, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "GL!TCH 3FF3CT",
            color = Color.Red,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(
                x = (-2 + offset * 4).dp,
                y = (-2 + offset * 4).dp
            )
        )
        Text(
            "GL!TCH 3FF3CT",
            color = Color.Blue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(
                x = (2 - offset * 4).dp,
                y = (2 - offset * 4).dp
            )
        )

        Text(
            "GLITCH EFFECT",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}  
""".trimIndent()