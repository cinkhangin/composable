package com.naulian.composable.screens.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.R
import com.naulian.composable.screens.neumorphic.NeuMorphicUP

@Composable
fun BottomBar(
    selectedEntry: MutableState<AppScreen>,
    bottomBarProperties: BottomBarProperties = BottomBarProperties(),
    onSelectItem: (AppScreen) -> Unit
) {
    val screens = remember { AppScreen.entries.toList() }
    val density = LocalDensity.current
    var rowWidthPx by remember { mutableFloatStateOf(0f) }
    var rowLeftPx by remember { mutableFloatStateOf(0f) }

    val weights = remember(selectedEntry.value) {
        screens.map { if (it == selectedEntry.value) 1.5f else 1f }
    }
    val totalWeight = weights.sum()

    val currentIndex = screens.indexOf(selectedEntry.value)
    val indicatorOffsetPx = rowLeftPx + (weights.take(currentIndex).sum() * rowWidthPx / totalWeight)
    val indicatorWidthPx = weights[currentIndex] * rowWidthPx / totalWeight

    val animatedOffsetPx by animateFloatAsState(
        targetValue = indicatorOffsetPx,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "indicator_animation"
    )

    val indicatorOffsetDp = with(density) { animatedOffsetPx.toDp() }
    val indicatorWidthDp = with(density) { indicatorWidthPx.toDp() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(bottomBarProperties.backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        NeuMorphicUP(
            modifier = Modifier
                .width(indicatorWidthDp)
                .height(50.dp)
                .offset(x = indicatorOffsetDp)
                .clip(RoundedCornerShape(bottomBarProperties.cornerRadius))
                .align(Alignment.CenterStart),
            contentAlignment = Alignment.CenterStart,
            lightColor = MaterialTheme.colorScheme.surfaceBright,
            shadowColor = MaterialTheme.colorScheme.surfaceDim
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .onGloballyPositioned { layoutCoordinates ->
                    rowWidthPx = layoutCoordinates.size.width.toFloat()
                    rowLeftPx = layoutCoordinates.positionInParent().x
                },
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            screens.forEachIndexed { index, screen ->
                BottomBarItem(
                    entry = screen,
                    isSelected = screen == selectedEntry.value,
                    properties = bottomBarProperties,
                    onSelect = {
                        if (screen == selectedEntry.value) return@BottomBarItem
                        onSelectItem(screen)
                    },
                    modifier = Modifier.weight(weights[index])
                )
            }
        }
    }
}


@Composable
private fun BottomBarItem(
    entry: AppScreen,
    isSelected: Boolean,
    properties: BottomBarProperties,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(entry.selectedIcon),
                contentDescription = stringResource(entry.title),
                tint = if (isSelected) MaterialTheme.colorScheme.onBackground else properties.iconTintColor
            )
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = stringResource(entry.title),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 12.dp),
                    fontSize = properties.fontSize,
                    fontWeight = properties.fontWeight,
                    fontFamily = properties.fontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

enum class AppScreen(
    val title: Int,
    val selectedIcon: Int,
    val route: String = "",
){
    Home(
        title = R.string.home,
        selectedIcon = R.drawable.ic_home_selected_icon,
        route = "",
    ),

    OverallProgress(
        title = R.string.progress,
        selectedIcon = R.drawable.ic_progress_icon,
        route = "",
    ),

    Settings(
        title = R.string.settings,
        selectedIcon = R.drawable.ic_settings_selected_icon,
        route = "",
    )
}


data class BottomBarProperties(
    var backgroundColor: Color = Color.White,
    var indicatorColor: Color = Color.DarkGray, // currently using neumorphic style
    var iconTintColor: Color = Color.Gray,
    var cornerRadius: Dp = 40.dp,
    var fontFamily: FontFamily = FontFamily.Default,
    var fontSize: TextUnit = 13.sp,
    var fontWeight: FontWeight = FontWeight.Normal,
)


val bottomBarCode by lazy {
    """
        @Composable
        fun BottomBar(
            selectedEntry: MutableState<AppScreen>,
            bottomBarProperties: BottomBarProperties = BottomBarProperties(),
            onSelectItem: (AppScreen) -> Unit
        ) {
            val screens = remember { AppScreen.entries.toList() }
            val density = LocalDensity.current
            var rowWidthPx by remember { mutableFloatStateOf(0f) }
            var rowLeftPx by remember { mutableFloatStateOf(0f) }

            val weights = remember(selectedEntry.value) {
                screens.map { if (it == selectedEntry.value) 1.5f else 1f }
            }
            val totalWeight = weights.sum()

            val currentIndex = screens.indexOf(selectedEntry.value)
            val indicatorOffsetPx = rowLeftPx + (weights.take(currentIndex).sum() * rowWidthPx / totalWeight)
            val indicatorWidthPx = weights[currentIndex] * rowWidthPx / totalWeight

            val animatedOffsetPx by animateFloatAsState(
                targetValue = indicatorOffsetPx,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                label = "indicator_animation"
            )

            val indicatorOffsetDp = with(density) { animatedOffsetPx.toDp() }
            val indicatorWidthDp = with(density) { indicatorWidthPx.toDp() }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(bottomBarProperties.backgroundColor)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                NeuMorphicUP(
                    modifier = Modifier
                        .width(indicatorWidthDp)
                        .height(50.dp)
                        .offset(x = indicatorOffsetDp)
                        .clip(RoundedCornerShape(bottomBarProperties.cornerRadius))
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.CenterStart,
                    lightColor = MaterialTheme.colorScheme.surfaceBright,
                    shadowColor = MaterialTheme.colorScheme.surfaceDim
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .onGloballyPositioned { layoutCoordinates ->
                            rowWidthPx = layoutCoordinates.size.width.toFloat()
                            rowLeftPx = layoutCoordinates.positionInParent().x
                        },
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    screens.forEachIndexed { index, screen ->
                        BottomBarItem(
                            entry = screen,
                            isSelected = screen == selectedEntry.value,
                            properties = bottomBarProperties,
                            onSelect = {
                                if (screen == selectedEntry.value) return@BottomBarItem
                                onSelectItem(screen)
                            },
                            modifier = Modifier.weight(weights[index])
                        )
                    }
                }
            }
        }
        
        
        @Composable
        private fun BottomBarItem(
            entry: AppScreen,
            isSelected: Boolean,
            properties: BottomBarProperties,
            onSelect: () -> Unit,
            modifier: Modifier = Modifier
        ) {
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onSelect
                    )
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(entry.selectedIcon),
                        contentDescription = stringResource(entry.title),
                        tint = if (isSelected) MaterialTheme.colorScheme.onBackground else properties.iconTintColor
                    )
                    AnimatedVisibility(visible = isSelected) {
                        Text(
                            text = stringResource(entry.title),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 12.dp),
                            fontSize = properties.fontSize,
                            fontWeight = properties.fontWeight,
                            fontFamily = properties.fontFamily,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        enum class AppScreen(
            val title: Int,
            val selectedIcon: Int,
            val route: String = "",
        ){
            Home(
                title = R.string.home,
                selectedIcon = R.drawable.ic_home_selected_icon,
                route = "",
            ),

            OverallProgress(
                title = R.string.progress,
                selectedIcon = R.drawable.ic_progress_icon,
                route = "",
            ),

            Settings(
                title = R.string.settings,
                selectedIcon = R.drawable.ic_settings_selected_icon,
                route = "",
            )
        }


        data class BottomBarProperties(
            var backgroundColor: Color = Color.White,
            var indicatorColor: Color = Color.DarkGray, // currently using neumorphic style
            var iconTintColor: Color = Color.Gray,
            var cornerRadius: Dp = 40.dp,
            var fontFamily: FontFamily = FontFamily.Default,
            var fontSize: TextUnit = 13.sp,
            var fontWeight: FontWeight = FontWeight.Normal,
        )
    """.trimIndent()
}
