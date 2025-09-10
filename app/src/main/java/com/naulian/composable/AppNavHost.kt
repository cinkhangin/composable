package com.naulian.composable

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naulian.composable.home.HomeScreen
import com.naulian.composable.screens.background.GridBackgroundScreen
import com.naulian.composable.screens.bottomBar.BottomBarScreen
import com.naulian.composable.screens.box.CorneredBoxScreen
import com.naulian.composable.screens.calenderTopBar.CalenderTopBarScreen
import com.naulian.composable.screens.cardCrousel.CarouselCard3DScreen
import com.naulian.composable.screens.neumorphic.NeumorphicScreen
import com.naulian.composable.screens.parallaxCards.ParallaxCardStackScreen
import com.naulian.composable.screens.rating.RatingStarsScreen

val LocalNavController = compositionLocalOf<NavController> {
    error("NavController not present")
}

@Composable
fun AppNavHost() {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController()
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
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(400)
                )
            },
        ) {
            composable<Screen.Home> {
                HomeScreen()
            }

            composable<Screen.Neumorphic> {
                NeumorphicScreen()
            }

            composable<Screen.GridBackground> {
                GridBackgroundScreen()
            }

            composable<Screen.CorneredBox> {
                CorneredBoxScreen()
            }

            composable<Screen.RatingStars> {
                RatingStarsScreen()
            }

            composable<Screen.ParallaxCardStack> {
                ParallaxCardStackScreen()
            }

            composable<Screen.Second> {
                SecondScreen()
            }

            composable<Screen.Carousel3DStack> {
                CarouselCard3DScreen(navController)
            }

            composable<Screen.BottomBar> {
                BottomBarScreen()
            }
            composable<Screen.CalenderTopBar> {
                CalenderTopBarScreen()
            }
        }
    }
}