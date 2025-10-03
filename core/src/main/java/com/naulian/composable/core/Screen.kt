package com.naulian.composable.core

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen // General

    // Static Components
    @Serializable
    data object StaticCC : Screen

    @Serializable
    data object Neumorphism : Screen

    @Serializable
    data object GridBackground : Screen

    @Serializable
    data object CorneredBox : Screen

    @Serializable
    data object GlassCard : Screen

    @Serializable
    data object MovieTicket : Screen

    @Serializable
    data object DepthCard: Screen

    // Interactive Components
    @Serializable
    data object InteractiveCC : Screen

    @Serializable
    data object RatingStars : Screen

    @Serializable
    data object ParallaxCardStack : Screen

    @Serializable
    data object BetterCarousel : Screen

    @Serializable
    data object StepsProgress : Screen

    @Serializable
    data object BottomBar : Screen

    @Serializable
    data object CalenderTopBar : Screen

    @Serializable
    data object CylindricalButtons: Screen

    @Serializable
    data object PhysicsButton : Screen

    // Animated Components
    @Serializable
    data object AnimatedCC : Screen 

    @Serializable
    data object Second : Screen // Unsure, leaving as is for now

    //Animated
    @Serializable
    data object TypingText : Screen

    @Serializable
    data object PulseHeart : Screen

    @Serializable
    data object GlitchEffect : Screen

    @Serializable
    data object Clock : Screen

    @Serializable
    data object CafeReceipt : Screen

    @Serializable
    data object Counter : Screen

    @Serializable
    data object AudioPlayer : Screen
}