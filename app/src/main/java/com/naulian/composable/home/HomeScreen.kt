package com.naulian.composable.home

import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController

@Composable
fun HomeScreen() {

    val navController = LocalNavController.current

    HomeScreenUI {
        when (it) {
            is HomeUIEvent.Navigate -> navController.navigate(it.route)
        }
    }
}