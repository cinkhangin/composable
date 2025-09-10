package com.naulian.composable

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen

    @Serializable
    data object Neumorphic : Screen

    @Serializable
    data object GridBackground : Screen

    @Serializable
    data object CorneredBox : Screen

    @Serializable
    data object RatingStars : Screen

    @Serializable
    data object Second : Screen

    @Serializable
    data object ParallaxCardStack : Screen

    @Serializable
    data object Carousel3DStack : Screen

    @Serializable
    data object BottomBar : Screen

    @Serializable
    data object CalenderTopBar : Screen
}