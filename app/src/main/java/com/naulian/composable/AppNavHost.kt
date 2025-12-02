package com.naulian.composable

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.naulian.composable.acc.accItemList
import com.naulian.composable.core.LocalComponents
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.DemoScreen
import com.naulian.composable.core.componentBuilder
import com.naulian.composable.core.rememberListDetailSceneStrategy
import com.naulian.composable.core.unaryPlus
import com.naulian.composable.home.HomeScreen
import com.naulian.composable.icc.iccItemList
import com.naulian.composable.scc.sccItemList

@Composable
fun AppNavHost() {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController(),
        LocalComponents provides componentBuilder {
            +sccItemList
            +accItemList
            +iccItemList
        }

    ) {
        val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
        val sceneStrategy = rememberListDetailSceneStrategy()

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

        LaunchedEffect(Unit) {
            if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                backStack.add(Screen.Demo(sccItemList.first().id))
            }
        }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            sceneStrategy = sceneStrategy,
            entryProvider = entryProvider {
                HomeScreen(backStack)
                DemoScreen(backStack)
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400)
                )
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400)
                )
            },
            popTransitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400)
                ) togetherWith slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400)
                )
            }
        )
    }
}