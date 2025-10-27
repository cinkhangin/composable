package com.naulian.composable

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.naulian.composable.acc.AnimatedCCScreen
import com.naulian.composable.acc.accItemList
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen
import com.naulian.composable.home.HomeScreen
import com.naulian.composable.icc.InteractiveCCScreen
import com.naulian.composable.scc.StaticCCScreen
import com.naulian.composable.core.LocalComponents
import com.naulian.composable.core.component.ComposableScreen
import com.naulian.composable.core.componentBuilder
import com.naulian.composable.core.unaryPlus
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
        val navController = LocalNavController.current as NavHostController
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400)
                )
            },
        ) {
            // General
            composable<Screen.Home> {
                HomeScreen()
            }

            // Static Components
            composable<Screen.StaticCC> {
                StaticCCScreen()
            }
            // Interactive Components
            composable<Screen.InteractiveCC> {
                InteractiveCCScreen()
            }
            // Animated Components
            composable<Screen.AnimatedCC> {
                AnimatedCCScreen()
            }

            composable<Screen.ComposableScreen> {
                val route = it.toRoute<Screen.ComposableScreen>()
                ComposableScreen(componentId = route.id)
            }
        }
    }
}