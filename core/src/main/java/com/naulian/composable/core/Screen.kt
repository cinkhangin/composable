package com.naulian.composable.core

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen // General

    @Serializable
    data class ComposableScreen(val id : String) : Screen

    // Static Components
    @Serializable
    data object StaticCC : Screen

    // Interactive Components
    @Serializable
    data object InteractiveCC : Screen

    // Animated Components
    @Serializable
    data object AnimatedCC : Screen
}