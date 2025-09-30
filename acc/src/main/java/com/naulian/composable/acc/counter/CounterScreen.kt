@file:JvmName("CounterScreenKt")

package com.naulian.composable.acc.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.columnItem
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CounterScreen() {
    val navController = LocalNavController.current

    CounterScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun CounterScreenUI(onBack: () -> Unit = {}) {
    val code = remember { animatedCounterCode }
    var count by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        count = 1000
    }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Animated Counter",
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
            columnItem(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedCounter(count = count, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(16.dp))

                CodeBlock(
                    source = code,
                    language = "kotlin",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

val animatedCounterCode = """
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier
) {
    val animatedCount by animateIntAsState(
        targetValue = count,
        label = "AnimatedCounter",
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = animatedCount.toString(),
            style = MaterialTheme.typography.displayLarge
        )
    }
}
""".trimIndent()
