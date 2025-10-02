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
import com.naulian.composable.acc.AnimatedCCScreen
import com.naulian.composable.acc.clock.ClockScreen
import com.naulian.composable.acc.counter.CounterScreen
import com.naulian.composable.acc.glitch.GlitchScreen
import com.naulian.composable.acc.pulse.PulseScreen
import com.naulian.composable.acc.typing.TypingTextScreen
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen
import com.naulian.composable.home.HomeScreen
import com.naulian.composable.icc.InteractiveCCScreen
import com.naulian.composable.icc.calender_topbar.CalenderTopBarScreen
import com.naulian.composable.icc.better_carousel.BetterCarouselScreen
import com.naulian.composable.icc.raised_button.RaisedButtonScreen
import com.naulian.composable.icc.stackable_item.StackableItemScreen
import com.naulian.composable.icc.physicsbutton.PhysicsButtonScreen
import com.naulian.composable.icc.rating.RatingStarsScreen
import com.naulian.composable.icc.step_progress.ProgressScreen
import com.naulian.composable.scc.StaticCCScreen
import com.naulian.composable.icc.audio_player.AudioPlayerScreen
import com.naulian.composable.scc.cafe_receipt.CafeReceiptScreen
import com.naulian.composable.scc.cornered_box.CorneredBoxScreen
import com.naulian.composable.scc.depth_card.DepthCardScreen
import com.naulian.composable.scc.glass.GlassCardScreen
import com.naulian.composable.scc.grid_background.GridBackgroundScreen
import com.naulian.composable.scc.neumorphic.NeumorphicScreen
import com.naulian.composable.scc.ticket.MovieTicketScreen

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
            composable<Screen.Neumorphism> {
                NeumorphicScreen()
            }
            composable<Screen.GridBackground> {
                GridBackgroundScreen()
            }
            composable<Screen.CorneredBox> {
                CorneredBoxScreen()
            }
            composable<Screen.GlassCard> {
                GlassCardScreen()
            }
            composable<Screen.MovieTicket> {
                MovieTicketScreen()
            }
            composable<Screen.DepthCard> {
                DepthCardScreen()
            }

            composable<Screen.CafeReceipt> {
                CafeReceiptScreen()
            }

            composable<Screen.AudioPlayer> {
                AudioPlayerScreen()
            }


            // Interactive Components
            composable<Screen.InteractiveCC> {
                InteractiveCCScreen()
            }
            composable<Screen.RatingStars> {
                RatingStarsScreen()
            }
            composable<Screen.ParallaxCardStack> {
                StackableItemScreen()
            }
            composable<Screen.BetterCarousel> {
                BetterCarouselScreen()
            }
            composable<Screen.StepsProgress> {
                ProgressScreen()
            }
            composable<Screen.BottomBar> {
                BottomBarScreen()
            }
            composable<Screen.CalenderTopBar> {
                CalenderTopBarScreen()
            }
            composable<Screen.CylindricalButtons> {
                RaisedButtonScreen()
            }
            composable<Screen.PhysicsButton> {
                PhysicsButtonScreen()
            }

            // Animated Components
            composable<Screen.AnimatedCC> {
                AnimatedCCScreen()
            }

            //Animated Composable Components
            composable<Screen.TypingText> {
                TypingTextScreen()
            }
            composable<Screen.PulseHeart> {
                PulseScreen()
            }
            composable<Screen.GlitchEffect> {
                GlitchScreen()
            }
            composable<Screen.Clock> {
                ClockScreen()
            }
            composable<Screen.Counter> {
                CounterScreen()
            }
        }
    }
}

@Composable
fun BottomBarScreen() {
    TODO("Not yet implemented")
}
