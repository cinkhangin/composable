package com.naulian.composable.screens.bottomBar

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun BottomBarScreen() {
    val navController = LocalNavController.current

    BottomBarUi(
        onBack = { navController.navigateUp() }
    )
}