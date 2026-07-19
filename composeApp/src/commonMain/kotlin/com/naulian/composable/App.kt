package com.naulian.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    ComposableTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var selectedComponent by remember { mutableStateOf(componentCatalog.first()) }
            var codePageComponent by remember { mutableStateOf<SharedComponent?>(null) }

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val wide = maxWidth >= 980.dp
                val pagePadding = if (wide) 34.dp else 18.dp

                val openComponent: (SharedComponent) -> Unit = { component ->
                    selectedComponent = component
                    codePageComponent = component
                }

                val visibleCodePageComponent = codePageComponent
                if (visibleCodePageComponent != null) {
                    ComponentCodePage(
                        component = visibleCodePageComponent,
                        wide = wide,
                        pagePadding = pagePadding,
                        onBack = { codePageComponent = null }
                    )
                } else if (wide) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pagePadding),
                        horizontalArrangement = Arrangement.spacedBy(34.dp)
                    ) {
                        GalleryIndex(
                            selectedComponent = selectedComponent,
                            onSelected = openComponent,
                            modifier = Modifier.width(286.dp)
                        )
                        GalleryWall(
                            selectedComponent = selectedComponent,
                            onSelected = openComponent,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(pagePadding),
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        item {
                            GalleryTitle(compact = true)
                        }
                        itemsIndexed(componentCatalog, key = { _, item -> item.id }) { index, component ->
                            val selected = selectedComponent.id == component.id
                            ComponentPlate(
                                index = index,
                                component = component,
                                selected = selected,
                                onSelected = { openComponent(component) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GalleryIndex(
    selectedComponent: SharedComponent,
    onSelected: (SharedComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
            GalleryTitle()

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                componentCatalog.forEachIndexed { index, component ->
                    IndexLine(
                        index = index,
                        component = component,
                        selected = selectedComponent.id == component.id,
                        onSelected = { onSelected(component) }
                    )
                }
            }
        }

        Text(
            text = "commonMain study",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = 1.2.sp
        )
    }
}

@Composable
private fun GalleryTitle(compact: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Composable",
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            fontSize = if (compact) 38.sp else 44.sp,
            lineHeight = if (compact) 42.sp else 48.sp
        )
        Text(
            text = "A quiet multiplatform gallery for Compose motion, shape, and interaction studies.",
            modifier = Modifier.widthIn(max = 360.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun IndexLine(
    index: Int,
    component: SharedComponent,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onSelected)
            .padding(vertical = 9.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = (index + 1).toString().padStart(2, '0'),
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = component.name,
            modifier = Modifier.weight(1f),
            color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GalleryWall(
    selectedComponent: SharedComponent,
    onSelected: (SharedComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        FeaturedStudy(component = selectedComponent)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 290.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            itemsIndexed(componentCatalog, key = { _, item -> item.id }) { index, component ->
                ComponentPlate(
                    index = index,
                    component = component,
                    selected = component.id == selectedComponent.id,
                    onSelected = { onSelected(component) }
                )
            }
        }
    }
}

@Composable
private fun FeaturedStudy(component: SharedComponent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.34f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Selected",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            letterSpacing = 1.4.sp
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = component.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = component.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ComponentPlate(
    index: Int,
    component: SharedComponent,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.72f)
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.24f)
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onSelected)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.28f)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            component.preview(Modifier.fillMaxSize())
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = (index + 1).toString().padStart(2, '0'),
                color = MaterialTheme.colorScheme.outline,
                style = MaterialTheme.typography.labelMedium
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = component.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = component.category,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (component.demo != null) {
            Spacer(modifier = Modifier.height(2.dp))
            component.demo.invoke()
        }
    }
}

@Composable
private fun ComponentCodePage(
    component: SharedComponent,
    wide: Boolean,
    pagePadding: androidx.compose.ui.unit.Dp,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(pagePadding),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Back to gallery",
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onBack)
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "commonMain code",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                    letterSpacing = 1.2.sp
                )
            }
        }

        item {
            if (wide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    CodePageIntro(
                        component = component,
                        modifier = Modifier.weight(0.38f)
                    )
                    ComponentPreviewFrame(
                        component = component,
                        modifier = Modifier.weight(0.62f)
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    CodePageIntro(component = component)
                    ComponentPreviewFrame(component = component)
                }
            }
        }

        item {
            CodePanel(component = component)
        }
    }
}

@Composable
private fun CodePageIntro(
    component: SharedComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = component.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Normal,
            fontSize = 42.sp,
            lineHeight = 46.sp
        )
        Text(
            text = component.category,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            letterSpacing = 1.2.sp
        )
        Text(
            text = component.description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 26.sp
        )
    }
}

@Composable
private fun ComponentPreviewFrame(
    component: SharedComponent,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(component.previewHeight.coerceAtLeast(260.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.26f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f))
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            component.preview(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun CodePanel(
    component: SharedComponent,
    modifier: Modifier = Modifier
) {
    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF171A18))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.28f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Code",
                color = Color(0xFFE7E8E2),
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 1.2.sp
            )
            Text(
                text = component.name,
                color = Color(0xFFAEB8AE),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFF101210))
                .padding(14.dp)
                .verticalScroll(verticalScroll)
                .horizontalScroll(horizontalScroll)
        ) {
            Text(
                text = component.sourceCode,
                color = Color(0xFFEDEEE9),
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }
    }
}
