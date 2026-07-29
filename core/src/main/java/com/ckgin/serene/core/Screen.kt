package com.ckgin.serene.core

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {

    @Serializable
    data object Home : Screen // General

    @Serializable
    data class Demo(val id : String) : Screen
}