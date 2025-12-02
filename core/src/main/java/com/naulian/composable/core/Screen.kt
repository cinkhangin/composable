package com.naulian.composable.core

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen // General

    @Serializable
    data class Demo(val id : String) : Screen
}