package com.naulian.composable.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.naulian.composable.core.ListDemoScene.Companion.DEMO_KEY
import com.naulian.composable.core.ListDemoScene.Companion.LIST_KEY

class ListDemoScene(
    override val key: Any,
    override val previousEntries: List<NavEntry<Screen>>,
    val listEntry: NavEntry<Screen>,
    val demoEntry: NavEntry<Screen>,
) : Scene<Screen> {
    override val entries: List<NavEntry<Screen>> = listOf(listEntry, demoEntry)
    override val content: @Composable (() -> Unit) = {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                listEntry.Content()
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            CompositionLocalProvider(LocalBackButtonVisibility provides false) {
                Column(modifier = Modifier.weight(1f)) {
                    AnimatedContent(
                        targetState = demoEntry,
                        contentKey = { entry -> entry.contentKey },
                        transitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = { it }
                            ) togetherWith slideOutHorizontally(targetOffsetX = { -it })
                        }
                    ) { entry ->
                        entry.Content()
                    }
                }
            }
        }
    }

    companion object Companion {
        internal const val LIST_KEY = "ListDemoScene-List"
        internal const val DEMO_KEY = "ListDemoScene-Demo"

        fun listPane() = mapOf(LIST_KEY to true)
        fun demoPane() = mapOf(DEMO_KEY to true)
    }
}

val LocalBackButtonVisibility = compositionLocalOf { true }

@Composable
fun rememberListDetailSceneStrategy(): ListDetailSceneStrategy {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    return remember(windowSizeClass) {
        ListDetailSceneStrategy(windowSizeClass)
    }
}


class ListDetailSceneStrategy(val windowSizeClass: WindowSizeClass) : SceneStrategy<Screen> {

    override fun SceneStrategyScope<Screen>.calculateScene(entries: List<NavEntry<Screen>>): Scene<Screen>? {

        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            return null
        }

        val detailEntry =
            entries.lastOrNull()?.takeIf { it.metadata.containsKey(DEMO_KEY) } ?: return null
        val listEntry = entries.findLast { it.metadata.containsKey(LIST_KEY) } ?: return null

        val sceneKey = listEntry.contentKey

        return ListDemoScene(
            key = sceneKey,
            previousEntries = entries.dropLast(1),
            listEntry = listEntry,
            demoEntry = detailEntry
        )
    }
}
