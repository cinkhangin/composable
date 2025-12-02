package com.naulian.composable.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import com.naulian.composable.core.ListDemoScene
import com.naulian.composable.core.Screen

@Composable
fun EntryProviderScope<Screen>.HomeScreen(backStack: SnapshotStateList<Screen>) {
    entry<Screen.Home>(
        metadata = ListDemoScene.listPane()
    ) {
        HomeScreenUI {
            when (it) {
                is HomeUIEvent.Navigate -> backStack.add(it.route)
            }
        }
    }
}