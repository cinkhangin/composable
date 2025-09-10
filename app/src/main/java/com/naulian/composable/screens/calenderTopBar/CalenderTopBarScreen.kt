package com.naulian.composable.screens.calenderTopBar

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun CalenderTopBarScreen() {
    val navController = LocalNavController.current

    CalenderTopBarUi(
        onBack = { navController.navigateUp() }
    )
}