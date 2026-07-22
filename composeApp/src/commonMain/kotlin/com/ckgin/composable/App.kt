package com.ckgin.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ckgin.modify.HugeIcons
import org.jetbrains.compose.resources.painterResource

private val StudioInk = Color(0xFF121316)
private val StudioPaper = Color(0xFFFAFAF7)
private val StudioLine = Color(0xFFD7D8D4)
private val StudioMuted = Color(0xFF686B70)
private val StudioBlue = Color(0xFF3158E7)
private val StudioCoral = Color(0xFFFF5D67)
private val StudioGreen = Color(0xFF149B72)

private enum class GalleryFilter(val label: String) {
    All("All objects"),
    Motion("Motion"),
    Interactive("Interactive"),
    Static("Static")
}

private enum class CollectionChapter(
    val title: String,
    val statement: String
) {
    Motion("Things that move.", "Animation, rhythm, time, and responsive motion."),
    Interactive("Things to touch.", "Controls that reward curiosity and invite a response."),
    Static("Things to hold.", "Structured surfaces with character and practical purpose.")
}

private data class StudioPalette(
    val field: Color,
    val accent: Color
)

private fun SharedComponent.matches(filter: GalleryFilter): Boolean = when (filter) {
    GalleryFilter.All -> true
    GalleryFilter.Motion -> category.contains("Animated") || category.contains("Canvas")
    GalleryFilter.Interactive -> category.contains("Interactive")
    GalleryFilter.Static -> category.contains("Static")
}

private fun SharedComponent.chapter(): CollectionChapter = when {
    category.contains("Interactive") -> CollectionChapter.Interactive
    category.contains("Static") -> CollectionChapter.Static
    else -> CollectionChapter.Motion
}

private fun paletteFor(category: String): StudioPalette = when {
    category.contains("Interactive") -> StudioPalette(Color(0xFFFFE5E9), StudioCoral)
    category.contains("Static") -> StudioPalette(Color(0xFFDFF4EC), StudioGreen)
    else -> StudioPalette(Color(0xFFE2E8FF), StudioBlue)
}

private fun filterColor(filter: GalleryFilter): Color = when (filter) {
    GalleryFilter.All -> StudioInk
    GalleryFilter.Motion -> StudioBlue
    GalleryFilter.Interactive -> StudioCoral
    GalleryFilter.Static -> StudioGreen
}

@Composable
fun App() {
    ComposableTheme(darkTheme = false) {
        var selectedComponent by remember { mutableStateOf(componentCatalog.first()) }
        var detailComponent by remember { mutableStateOf<SharedComponent?>(null) }
        var activeFilter by remember { mutableStateOf(GalleryFilter.All) }
        val galleryState = rememberLazyListState()
        val visibleComponents = remember(activeFilter) {
            componentCatalog.filter { it.matches(activeFilter) }
        }

        val selectFilter: (GalleryFilter) -> Unit = { filter ->
            activeFilter = filter
            val filtered = componentCatalog.filter { it.matches(filter) }
            if (filtered.none { it.id == selectedComponent.id }) {
                selectedComponent = filtered.first()
            }
        }
        val moveDetail: (Int) -> Unit = { delta ->
            val current = detailComponent ?: selectedComponent
            val currentIndex = componentCatalog.indexOfFirst { it.id == current.id }.coerceAtLeast(0)
            val nextIndex = (currentIndex + delta + componentCatalog.size) % componentCatalog.size
            val next = componentCatalog[nextIndex]
            selectedComponent = next
            detailComponent = next
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = StudioPaper
        ) {
            val detail = detailComponent
            if (detail == null) {
                ObjectStudio(
                    visibleComponents = visibleComponents,
                    activeFilter = activeFilter,
                    listState = galleryState,
                    onFilterSelected = selectFilter,
                    onOpen = { component ->
                        selectedComponent = component
                        detailComponent = component
                    }
                )
            } else {
                ObjectDetail(
                    component = detail,
                    onBack = { detailComponent = null },
                    onPrevious = { moveDetail(-1) },
                    onNext = { moveDetail(1) }
                )
            }
        }
    }
}

@Composable
private fun ObjectStudio(
    visibleComponents: List<SharedComponent>,
    activeFilter: GalleryFilter,
    listState: LazyListState,
    onFilterSelected: (GalleryFilter) -> Unit,
    onOpen: (SharedComponent) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val wide = maxWidth >= 920.dp
        ScrollableStudio(
            components = visibleComponents,
            activeFilter = activeFilter,
            wide = wide,
            viewportHeight = maxHeight,
            listState = listState,
            onFilterSelected = onFilterSelected,
            onOpen = onOpen
        )
    }
}

@Composable
private fun ScrollableStudio(
    components: List<SharedComponent>,
    activeFilter: GalleryFilter,
    wide: Boolean,
    viewportHeight: Dp,
    listState: LazyListState,
    onFilterSelected: (GalleryFilter) -> Unit,
    onOpen: (SharedComponent) -> Unit
) {
    val chapters = remember(components) {
        CollectionChapter.entries.mapNotNull { chapter ->
            val chapterComponents = components.filter { it.chapter() == chapter }
            if (chapterComponents.isEmpty()) null else chapter to chapterComponents
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(StudioPaper),
        state = listState,
        contentPadding = PaddingValues(bottom = 0.dp)
    ) {
        item {
            ArtBookCover(
                wide = wide,
                viewportHeight = viewportHeight,
                activeFilter = activeFilter,
                onFilterSelected = onFilterSelected
            )
        }
        itemsIndexed(chapters, key = { _, chapter -> chapter.first.name }) { index, chapter ->
            ChapterPage(
                sequence = index,
                chapter = chapter.first,
                components = chapter.second,
                wide = wide,
                viewportHeight = viewportHeight,
                onOpen = onOpen
            )
        }
        item { StudioFooter() }
    }
}

@Composable
private fun ArtBookCover(
    wide: Boolean,
    viewportHeight: Dp,
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit
) {
    val coverHeight = viewportHeight.coerceAtLeast(if (wide) 650.dp else 700.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(coverHeight)
            .background(StudioInk)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = if (wide) 34.dp else 18.dp, vertical = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier.size(27.dp).background(StudioPaper),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "C",
                        color = StudioInk,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = 17.sp
                    )
                }
                Text(
                    text = "COMPOSABLE",
                    color = StudioPaper,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.4.sp
                )
            }
            Text(
                text = "OBJECT BOOK  /  01-${componentCatalog.size}",
                color = StudioPaper.copy(alpha = 0.52f),
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.1.sp
            )
        }

        if (wide) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 42.dp, bottom = 38.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                CoverWord("COM", StudioPaper, 94)
                CoverWord("POS", StudioBlue, 94)
                CoverWord("ABLE", StudioPaper, 94)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.52f)
                    .fillMaxHeight(0.61f)
                    .align(Alignment.CenterEnd)
                    .padding(end = 42.dp)
                    .background(StudioCoral)
            )
            ArtObjectPreview(
                component = componentCatalog[3],
                field = StudioPaper,
                accent = StudioBlue,
                modifier = Modifier
                    .fillMaxWidth(0.50f)
                    .fillMaxHeight(0.61f)
                    .align(Alignment.CenterEnd)
                    .padding(end = 62.dp, bottom = 20.dp)
                    .graphicsLayer { rotationZ = -1.1f }
            )
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 44.dp, bottom = 112.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "USEFUL / EXPRESSIVE / SHARED",
                    color = StudioGreen,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp
                )
                Text(
                    text = "Scroll through a living edition of interface objects.",
                    color = StudioPaper.copy(alpha = 0.62f),
                    style = MaterialTheme.typography.bodySmall,
                    lineHeight = 19.sp
                )
            }
        } else {
            Column(
                modifier = Modifier.align(Alignment.TopStart).padding(start = 18.dp, top = 88.dp)
            ) {
                CoverWord("COM", StudioPaper, 56)
                CoverWord("POS", StudioBlue, 56)
                CoverWord("ABLE", StudioPaper, 56)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .fillMaxHeight(0.43f)
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp, top = 92.dp)
                    .background(StudioCoral)
            )
            ArtObjectPreview(
                component = componentCatalog[3],
                field = StudioPaper,
                accent = StudioBlue,
                modifier = Modifier
                    .fillMaxWidth(0.86f)
                    .fillMaxHeight(0.43f)
                    .align(Alignment.CenterEnd)
                    .padding(end = 24.dp, top = 76.dp, bottom = 14.dp)
                    .graphicsLayer { rotationZ = -1f }
            )
            Text(
                text = "A LIVING EDITION OF USEFUL INTERFACE OBJECTS",
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 18.dp, bottom = 104.dp),
                color = StudioGreen,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.25.sp
            )
        }

        CoverFilters(
            activeFilter = activeFilter,
            onFilterSelected = onFilterSelected,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(horizontal = if (wide) 34.dp else 18.dp, vertical = 22.dp)
        )
    }
}

@Composable
private fun CoverWord(text: String, color: Color, size: Int) {
    Text(
        text = text,
        color = color,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        fontSize = size.sp,
        lineHeight = (size - 10).sp
    )
}

@Composable
private fun CoverFilters(
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "VIEW",
            color = StudioPaper.copy(alpha = 0.38f),
            style = MaterialTheme.typography.labelSmall,
            letterSpacing = 1.4.sp
        )
        GalleryFilter.entries.forEach { filter ->
            val selected = filter == activeFilter
            Column(
                modifier = Modifier.clickable { onFilterSelected(filter) }.padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = filter.label.uppercase(),
                    color = if (selected) StudioPaper else StudioPaper.copy(alpha = 0.46f),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    letterSpacing = 1.1.sp
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(if (selected) filterColor(filter) else Color.Transparent)
                )
            }
        }
    }
}

@Composable
private fun ChapterPage(
    sequence: Int,
    chapter: CollectionChapter,
    components: List<SharedComponent>,
    wide: Boolean,
    viewportHeight: Dp,
    onOpen: (SharedComponent) -> Unit
) {
    val dark = chapter == CollectionChapter.Interactive
    val accent = when (chapter) {
        CollectionChapter.Motion -> StudioBlue
        CollectionChapter.Interactive -> StudioCoral
        CollectionChapter.Static -> StudioGreen
    }
    val field = when (chapter) {
        CollectionChapter.Motion -> Color(0xFFE2E8FF)
        CollectionChapter.Interactive -> StudioInk
        CollectionChapter.Static -> Color(0xFFDFF4EC)
    }
    val ink = if (dark) StudioPaper else StudioInk
    val muted = if (dark) StudioPaper.copy(alpha = 0.58f) else StudioMuted
    val pageHeight = viewportHeight.coerceAtLeast(if (wide) 650.dp else 720.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(pageHeight)
            .background(field)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = if (wide) 34.dp else 18.dp,
                    vertical = if (wide) 26.dp else 20.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    text = "CHAPTER ${(sequence + 1).toString().padStart(2, '0')}",
                    color = accent,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.4.sp
                )
                Text(
                    text = chapter.title,
                    color = ink,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = if (wide) 48.sp else 37.sp,
                    lineHeight = if (wide) 49.sp else 39.sp
                )
            }
            if (wide) {
                Column(
                    modifier = Modifier.width(290.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "${components.size.toString().padStart(2, '0')} OBJECTS",
                        color = accent,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = chapter.statement,
                        color = muted,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 19.sp
                    )
                }
            } else {
                Text(
                    text = components.size.toString().padStart(2, '0'),
                    color = accent,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 32.sp
                )
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(
                start = if (wide) 34.dp else 18.dp,
                top = 8.dp,
                end = if (wide) 34.dp else 18.dp,
                bottom = 18.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(if (wide) 20.dp else 14.dp)
        ) {
            itemsIndexed(components, key = { _, component -> component.id }) { index, component ->
                ChapterObjectSheet(
                    component = component,
                    ordinal = index,
                    wide = wide,
                    accent = accent,
                    darkChapter = dark,
                    onOpen = { onOpen(component) },
                    modifier = Modifier
                        .width(if (wide) 410.dp else 292.dp)
                        .fillMaxHeight()
                        .padding(
                            top = if (index % 2 == 1) 20.dp else 0.dp,
                            bottom = if (index % 2 == 0) 20.dp else 0.dp
                        )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (wide) 34.dp else 18.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = chapter.statement.uppercase(),
                modifier = Modifier.weight(1f).padding(end = 16.dp),
                color = muted,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.05.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "DRAG TO EXPLORE",
                    color = ink,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.1.sp
                )
                Icon(
                    painter = painterResource(HugeIcons.Next),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = accent
                )
            }
        }
    }
}

@Composable
private fun ChapterObjectSheet(
    component: SharedComponent,
    ordinal: Int,
    wide: Boolean,
    accent: Color,
    darkChapter: Boolean,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val componentPalette = paletteFor(component.category)
    Column(
        modifier = modifier
            .background(StudioPaper)
            .clickable(onClick = onOpen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(componentPalette.field)
                .drawBehind {
                    val guide = accent.copy(alpha = 0.15f)
                    drawLine(guide, Offset(size.width / 2f, 0f), Offset(size.width / 2f, size.height))
                    drawLine(guide, Offset(0f, size.height / 2f), Offset(size.width, size.height / 2f))
                }
                .padding(if (wide) 24.dp else 18.dp),
            contentAlignment = Alignment.Center
        ) {
            component.preview(Modifier.fillMaxSize())
            Text(
                text = (componentCatalog.indexOf(component) + 1).toString().padStart(2, '0'),
                modifier = Modifier.align(Alignment.TopStart),
                color = accent,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.1.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (wide) 86.dp else 78.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = component.name,
                    color = StudioInk,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = if (wide) 24.sp else 21.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "OBJECT ${(ordinal + 1).toString().padStart(2, '0')}",
                    color = StudioMuted,
                    style = MaterialTheme.typography.labelSmall,
                    letterSpacing = 1.sp
                )
            }
            Icon(
                painter = painterResource(HugeIcons.Next),
                contentDescription = "Open ${component.name}",
                modifier = Modifier.size(18.dp),
                tint = if (darkChapter) StudioCoral else accent
            )
        }
    }
}

@Composable
private fun ArtBookPage(
    sequence: Int,
    component: SharedComponent,
    wide: Boolean,
    viewportHeight: Dp,
    onOpen: () -> Unit
) {
    val index = componentCatalog.indexOf(component)
    val palette = paletteFor(component.category)
    val dark = sequence % 3 == 1
    val mirrored = sequence % 2 == 1
    val pageColor = when {
        dark -> StudioInk
        sequence % 3 == 2 -> StudioPaper
        else -> palette.field
    }
    val ink = if (dark) StudioPaper else StudioInk
    val muted = if (dark) StudioPaper.copy(alpha = 0.58f) else StudioMuted
    val pageHeight = viewportHeight.coerceAtLeast(if (wide) 650.dp else 720.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(pageHeight)
            .background(pageColor)
            .clickable(onClick = onOpen)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = if (wide) 34.dp else 18.dp, vertical = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${(index + 1).toString().padStart(2, '0')} / ${componentCatalog.size}",
                color = palette.accent,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.4.sp
            )
            Text(
                text = component.category.substringBefore(" Compose").uppercase(),
                color = muted,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.1.sp
            )
        }

        Text(
            text = (index + 1).toString().padStart(2, '0'),
            modifier = Modifier
                .align(if (mirrored) Alignment.BottomStart else Alignment.BottomEnd)
                .padding(horizontal = 22.dp, vertical = 18.dp),
            color = palette.accent.copy(alpha = if (dark) 0.22f else 0.13f),
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = if (wide) 126.sp else 78.sp,
            lineHeight = if (wide) 126.sp else 78.sp
        )

        if (wide) {
            ArtObjectPreview(
                component = component,
                field = if (dark) StudioPaper else palette.field,
                accent = palette.accent,
                modifier = Modifier
                    .fillMaxWidth(0.61f)
                    .fillMaxHeight(0.68f)
                    .align(if (mirrored) Alignment.CenterStart else Alignment.CenterEnd)
                    .padding(
                        start = if (mirrored) 40.dp else 0.dp,
                        end = if (mirrored) 0.dp else 40.dp,
                        top = 24.dp
                    )
            )
            ArtBookInfo(
                component = component,
                ink = ink,
                muted = muted,
                accent = palette.accent,
                modifier = Modifier
                    .width(300.dp)
                    .align(if (mirrored) Alignment.CenterEnd else Alignment.CenterStart)
                    .padding(
                        start = if (mirrored) 0.dp else 42.dp,
                        end = if (mirrored) 42.dp else 0.dp,
                        top = 52.dp
                    )
            )
        } else {
            ArtObjectPreview(
                component = component,
                field = if (dark) StudioPaper else palette.field,
                accent = palette.accent,
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .fillMaxHeight(0.49f)
                    .align(Alignment.TopCenter)
                    .padding(top = 72.dp)
            )
            ArtBookInfo(
                component = component,
                ink = ink,
                muted = muted,
                accent = palette.accent,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, end = 54.dp, bottom = 78.dp)
            )
        }
    }
}

@Composable
private fun ArtObjectPreview(
    component: SharedComponent,
    field: Color,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(field)
            .drawBehind {
                val guide = accent.copy(alpha = 0.17f)
                drawLine(guide, Offset(size.width / 2f, 0f), Offset(size.width / 2f, size.height))
                drawLine(guide, Offset(0f, size.height / 2f), Offset(size.width, size.height / 2f))
                drawCircle(guide, radius = 24f, center = center, style = Stroke(width = 2f))
            }
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        component.preview(Modifier.fillMaxSize())
    }
}

@Composable
private fun ArtBookInfo(
    component: SharedComponent,
    ink: Color,
    muted: Color,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(Modifier.width(58.dp).height(5.dp).background(accent))
        Text(
            text = component.name,
            color = ink,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = 42.sp,
            lineHeight = 43.sp
        )
        Text(
            text = component.description,
            color = muted,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 21.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = "ENTER OBJECT",
                color = ink,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.25.sp
            )
            Icon(
                painter = painterResource(HugeIcons.Next),
                contentDescription = "Open ${component.name}",
                modifier = Modifier.size(17.dp),
                tint = accent
            )
        }
    }
}

@Composable
private fun StudioIntroduction(wide: Boolean) {
    val count = componentCatalog.size.toString().padStart(2, '0')
    if (wide) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(268.dp)
                .background(StudioInk)
                .padding(horizontal = 42.dp, vertical = 34.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "THE OBJECT STUDIO",
                    color = StudioGreen,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Useful things,\nbeautifully considered.",
                    color = StudioPaper,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 52.sp,
                    lineHeight = 51.sp
                )
            }
            Column(
                modifier = Modifier.width(300.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Box(Modifier.width(44.dp).height(5.dp).background(StudioBlue))
                    Box(Modifier.width(21.dp).height(5.dp).background(StudioCoral))
                    Box(Modifier.width(31.dp).height(5.dp).background(StudioGreen))
                }
                Text(
                    text = "$count live interface objects. Scroll the collection, then enter any object to inspect and move through the complete series.",
                    color = StudioPaper.copy(alpha = 0.64f),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StudioInk)
                .padding(horizontal = 18.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Text(
                text = "THE OBJECT STUDIO  /  $count OBJECTS",
                color = StudioGreen,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.3.sp
            )
            Text(
                text = "Useful things,\nbeautifully considered.",
                color = StudioPaper,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 39.sp,
                lineHeight = 40.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                Box(Modifier.width(44.dp).height(5.dp).background(StudioBlue))
                Box(Modifier.width(21.dp).height(5.dp).background(StudioCoral))
                Box(Modifier.width(31.dp).height(5.dp).background(StudioGreen))
            }
        }
    }
}

@Composable
private fun ScrollableObject(
    component: SharedComponent,
    wide: Boolean,
    onOpen: () -> Unit
) {
    val index = componentCatalog.indexOf(component)
    val palette = paletteFor(component.category)
    Column(modifier = Modifier.fillMaxWidth().background(StudioPaper)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (wide) 28.dp else 16.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(Modifier.width(34.dp).height(4.dp).background(palette.accent))
                Text(
                    text = "OBJECT ${(index + 1).toString().padStart(2, '0')}",
                    color = StudioInk,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )
            }
            Text(
                text = component.category.substringBefore(" Compose").uppercase(),
                color = StudioMuted,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.sp
            )
        }
        StudioDivider()

        if (wide) {
            Row(modifier = Modifier.fillMaxWidth().height(component.previewHeight.coerceIn(420.dp, 560.dp))) {
                IndexSpine(
                    index = index,
                    total = componentCatalog.size,
                    accent = palette.accent,
                    modifier = Modifier.width(82.dp).fillMaxHeight()
                )
                StudioDivider(vertical = true)
                ObjectStage(
                    component = component,
                    index = index,
                    onOpen = onOpen,
                    modifier = Modifier.weight(0.65f).fillMaxHeight()
                )
                StudioDivider(vertical = true)
                LandingObjectInfo(
                    component = component,
                    onOpen = onOpen,
                    modifier = Modifier.weight(0.35f).fillMaxHeight()
                )
            }
        } else {
            ObjectStage(
                component = component,
                index = index,
                onOpen = onOpen,
                modifier = Modifier.fillMaxWidth().height(component.previewHeight.coerceIn(320.dp, 430.dp))
            )
            LandingObjectInfo(
                component = component,
                onOpen = onOpen,
                modifier = Modifier.fillMaxWidth().height(260.dp)
            )
        }
        StudioDivider()
        Spacer(Modifier.height(if (wide) 54.dp else 30.dp))
    }
}

@Composable
private fun LandingObjectInfo(
    component: SharedComponent,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = paletteFor(component.category).accent
    Column(
        modifier = modifier.background(StudioPaper).padding(28.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(Modifier.width(52.dp).height(5.dp).background(accent))
            Text(
                text = component.name,
                color = StudioInk,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 39.sp,
                lineHeight = 41.sp
            )
            Text(
                text = component.description,
                color = StudioMuted,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 21.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(StudioInk)
                .clickable(onClick = onOpen)
                .padding(horizontal = 15.dp, vertical = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ENTER OBJECT",
                color = StudioPaper,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Icon(
                painter = painterResource(HugeIcons.Next),
                contentDescription = null,
                modifier = Modifier.size(17.dp),
                tint = accent
            )
        }
    }
}

@Composable
private fun DesktopStudio(
    selectedComponent: SharedComponent,
    visibleComponents: List<SharedComponent>,
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    onComponentSelected: (SharedComponent) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onOpen: () -> Unit
) {
    val globalIndex = componentCatalog.indexOf(selectedComponent)
    Column(modifier = Modifier.fillMaxSize().background(StudioPaper)) {
        StudioHeader(wide = true)
        StudioDivider()
        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
            IndexSpine(
                index = globalIndex,
                total = componentCatalog.size,
                accent = paletteFor(selectedComponent.category).accent,
                modifier = Modifier.width(82.dp).fillMaxHeight()
            )
            StudioDivider(vertical = true)
            ObjectStage(
                component = selectedComponent,
                index = globalIndex,
                onOpen = onOpen,
                modifier = Modifier.weight(0.64f).fillMaxHeight()
            )
            StudioDivider(vertical = true)
            ComponentInfo(
                component = selectedComponent,
                onPrevious = onPrevious,
                onNext = onNext,
                onOpen = onOpen,
                modifier = Modifier.weight(0.36f).fillMaxHeight()
            )
        }
        StudioDivider()
        CollectionDock(
            selectedComponent = selectedComponent,
            components = visibleComponents,
            activeFilter = activeFilter,
            onFilterSelected = onFilterSelected,
            onComponentSelected = onComponentSelected
        )
    }
}

@Composable
private fun MobileStudio(
    selectedComponent: SharedComponent,
    visibleComponents: List<SharedComponent>,
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    onComponentSelected: (SharedComponent) -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onOpen: () -> Unit
) {
    val globalIndex = componentCatalog.indexOf(selectedComponent)
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(StudioPaper),
        contentPadding = PaddingValues(bottom = 0.dp)
    ) {
        item { StudioHeader(wide = false) }
        item { StudioDivider() }
        item {
            MobileObjectMarker(
                index = globalIndex,
                accent = paletteFor(selectedComponent.category).accent
            )
        }
        item {
            ObjectStage(
                component = selectedComponent,
                index = globalIndex,
                onOpen = onOpen,
                modifier = Modifier.fillMaxWidth().height(370.dp)
            )
        }
        item {
            ComponentInfo(
                component = selectedComponent,
                onPrevious = onPrevious,
                onNext = onNext,
                onOpen = onOpen,
                modifier = Modifier.fillMaxWidth().height(330.dp)
            )
        }
        item { StudioDivider() }
        item {
            MobileCollectionDock(
                selectedComponent = selectedComponent,
                components = visibleComponents,
                activeFilter = activeFilter,
                onFilterSelected = onFilterSelected,
                onComponentSelected = onComponentSelected
            )
        }
        item { StudioFooter() }
    }
}

@Composable
private fun StudioHeader(wide: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (wide) 70.dp else 62.dp)
            .padding(horizontal = if (wide) 24.dp else 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Box(
                modifier = Modifier.size(29.dp).background(StudioInk),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "C",
                    color = StudioPaper,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp
                )
            }
            Text(
                text = "COMPOSABLE",
                color = StudioInk,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.3.sp
            )
        }
        if (wide) {
            Text(
                text = "OBJECT STUDIO  /  USEFUL INTERFACE STUDIES",
                color = StudioMuted,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = if (wide) "ANDROID  IOS  DESKTOP  WEB" else "KMP / CMP",
            color = StudioMuted,
            style = MaterialTheme.typography.labelSmall,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun IndexSpine(
    index: Int,
    total: Int,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(StudioInk).padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = (index + 1).toString().padStart(2, '0'),
                color = StudioPaper,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 28.sp
            )
            Box(Modifier.width(3.dp).height(28.dp).background(accent))
        }
        Text(
            text = "OBJECT STUDIO",
            modifier = Modifier.graphicsLayer { rotationZ = -90f },
            color = StudioPaper.copy(alpha = 0.62f),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            maxLines = 1
        )
        Text(
            text = total.toString().padStart(2, '0'),
            color = StudioPaper.copy(alpha = 0.46f),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun MobileObjectMarker(index: Int, accent: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(StudioInk)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Box(Modifier.width(28.dp).height(3.dp).background(accent))
            Text(
                text = "OBJECT ${(index + 1).toString().padStart(2, '0')}",
                color = StudioPaper,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = componentCatalog.size.toString().padStart(2, '0'),
            color = StudioPaper.copy(alpha = 0.5f),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun ObjectStage(
    component: SharedComponent,
    index: Int,
    onOpen: (() -> Unit)?,
    showDemo: Boolean = false,
    modifier: Modifier = Modifier
) {
    val palette = paletteFor(component.category)
    val interaction = if (onOpen != null) Modifier.clickable(onClick = onOpen) else Modifier
    Box(
        modifier = modifier
            .background(palette.field)
            .drawBehind {
                val guide = palette.accent.copy(alpha = 0.18f)
                drawLine(guide, Offset(size.width / 2f, 0f), Offset(size.width / 2f, size.height))
                drawLine(guide, Offset(0f, size.height / 2f), Offset(size.width, size.height / 2f))
                drawCircle(
                    color = guide,
                    radius = 30f,
                    center = center,
                    style = Stroke(width = 2f)
                )
            }
            .then(interaction)
            .padding(22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LIVE OBJECT  /  ${(index + 1).toString().padStart(2, '0')}",
                color = palette.accent,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.3.sp
            )
            if (onOpen != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "OPEN",
                        color = StudioInk,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Icon(
                        painter = painterResource(HugeIcons.Next),
                        contentDescription = "Open ${component.name}",
                        modifier = Modifier.size(17.dp),
                        tint = StudioInk
                    )
                }
            }
        }

        Text(
            text = (index + 1).toString().padStart(2, '0'),
            modifier = Modifier.align(Alignment.BottomStart),
            color = palette.accent.copy(alpha = 0.13f),
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = 92.sp,
            lineHeight = 92.sp
        )

        AnimatedContent(
            targetState = component.id,
            modifier = Modifier.fillMaxSize().padding(top = 34.dp, bottom = 14.dp),
            transitionSpec = {
                fadeIn(tween(260)) togetherWith fadeOut(tween(180))
            },
            label = "object-stage"
        ) { id ->
            val visibleComponent = componentCatalog.first { it.id == id }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val demo = visibleComponent.demo
                if (showDemo && demo != null) {
                    demo()
                } else {
                    visibleComponent.preview(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun ComponentInfo(
    component: SharedComponent,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = paletteFor(component.category)
    Column(
        modifier = modifier.background(StudioPaper).padding(28.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Box(Modifier.width(54.dp).height(5.dp).background(palette.accent))
            Text(
                text = component.category.uppercase(),
                color = palette.accent,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.3.sp
            )
            Text(
                text = component.name,
                color = StudioInk,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 43.sp,
                lineHeight = 44.sp
            )
            Text(
                text = component.description,
                color = StudioMuted,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp
            )
            Text(
                text = "COMMONMAIN  /  REUSABLE OBJECT",
                color = StudioMuted.copy(alpha = 0.72f),
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.1.sp
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextControl(
                    label = "Previous",
                    icon = HugeIcons.Back,
                    onClick = onPrevious,
                    modifier = Modifier.weight(1f)
                )
                TextControl(
                    label = "Next",
                    icon = HugeIcons.Next,
                    iconAfter = true,
                    onClick = onNext,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(StudioInk)
                    .clickable(onClick = onOpen)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VIEW OBJECT + SOURCE",
                    color = StudioPaper,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Icon(
                    painter = painterResource(HugeIcons.Next),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = palette.accent
                )
            }
        }
    }
}

@Composable
private fun TextControl(
    label: String,
    icon: org.jetbrains.compose.resources.DrawableResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconAfter: Boolean = false
) {
    Row(
        modifier = modifier
            .border(1.dp, StudioLine)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!iconAfter) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = StudioInk
            )
            Spacer(Modifier.width(7.dp))
        }
        Text(
            text = label,
            color = StudioInk,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
        if (iconAfter) {
            Spacer(Modifier.width(7.dp))
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                tint = StudioInk
            )
        }
    }
}

@Composable
private fun CollectionDock(
    selectedComponent: SharedComponent,
    components: List<SharedComponent>,
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    onComponentSelected: (SharedComponent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().height(146.dp).background(StudioPaper)) {
        FilterRow(
            activeFilter = activeFilter,
            onFilterSelected = onFilterSelected,
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 22.dp)
        )
        StudioDivider()
        ComponentIndex(
            selectedComponent = selectedComponent,
            components = components,
            onComponentSelected = onComponentSelected,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
    }
}

@Composable
private fun MobileCollectionDock(
    selectedComponent: SharedComponent,
    components: List<SharedComponent>,
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    onComponentSelected: (SharedComponent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().background(StudioPaper)) {
        Text(
            text = "COLLECTION INDEX",
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 12.dp),
            color = StudioInk,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.4.sp
        )
        FilterRow(
            activeFilter = activeFilter,
            onFilterSelected = onFilterSelected,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        ComponentIndex(
            selectedComponent = selectedComponent,
            components = components,
            onComponentSelected = onComponentSelected,
            modifier = Modifier.fillMaxWidth().height(106.dp)
        )
    }
}

@Composable
private fun FilterRow(
    activeFilter: GalleryFilter,
    onFilterSelected: (GalleryFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GalleryFilter.entries.forEach { filter ->
            val selected = filter == activeFilter
            Row(
                modifier = Modifier.clickable { onFilterSelected(filter) }.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Box(
                    Modifier
                        .size(if (selected) 8.dp else 5.dp)
                        .background(if (selected) filterColor(filter) else StudioLine)
                )
                Text(
                    text = filter.label,
                    color = if (selected) StudioInk else StudioMuted,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = componentCatalog.count { it.matches(filter) }.toString().padStart(2, '0'),
                    color = if (selected) filterColor(filter) else StudioMuted.copy(alpha = 0.55f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun ComponentIndex(
    selectedComponent: SharedComponent,
    components: List<SharedComponent>,
    onComponentSelected: (SharedComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        components.forEach { component ->
            val selected = component.id == selectedComponent.id
            val accent = paletteFor(component.category).accent
            Column(
                modifier = Modifier
                    .widthIn(min = 118.dp, max = 174.dp)
                    .clickable { onComponentSelected(component) },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = (componentCatalog.indexOf(component) + 1).toString().padStart(2, '0'),
                    color = if (selected) accent else StudioMuted.copy(alpha = 0.56f),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = component.name,
                    color = if (selected) StudioInk else StudioMuted,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(if (selected) 3.dp else 1.dp)
                        .background(if (selected) accent else StudioLine)
                )
            }
        }
    }
}

@Composable
private fun ObjectDetail(
    component: SharedComponent,
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize().background(StudioPaper)) {
        val wide = maxWidth >= 920.dp
        if (wide) {
            Column(modifier = Modifier.fillMaxSize()) {
                DetailHeader(
                    component = component,
                    wide = true,
                    onBack = onBack,
                    onPrevious = onPrevious,
                    onNext = onNext
                )
                StudioDivider()
                Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    ObjectStage(
                        component = component,
                        index = componentCatalog.indexOf(component),
                        onOpen = null,
                        showDemo = true,
                        modifier = Modifier.weight(0.56f).fillMaxHeight()
                    )
                    StudioDivider(vertical = true)
                    DetailSource(
                        component = component,
                        modifier = Modifier.weight(0.44f).fillMaxHeight()
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    DetailHeader(
                        component = component,
                        wide = false,
                        onBack = onBack,
                        onPrevious = onPrevious,
                        onNext = onNext
                    )
                }
                item { StudioDivider() }
                item {
                    ObjectStage(
                        component = component,
                        index = componentCatalog.indexOf(component),
                        onOpen = null,
                        showDemo = true,
                        modifier = Modifier.fillMaxWidth().height(component.previewHeight.coerceIn(330.dp, 440.dp))
                    )
                }
                item {
                    DetailSource(
                        component = component,
                        modifier = Modifier.fillMaxWidth().height(660.dp)
                    )
                }
                item { StudioFooter() }
            }
        }
    }
}

@Composable
private fun DetailHeader(
    component: SharedComponent,
    wide: Boolean,
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val index = componentCatalog.indexOf(component)
    Row(
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(horizontal = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onBack).padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Icon(
                painter = painterResource(HugeIcons.Back),
                contentDescription = "Back to object studio",
                modifier = Modifier.size(18.dp),
                tint = StudioInk
            )
            Text(
                text = if (wide) "OBJECT STUDIO" else "BACK",
                color = StudioInk,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (wide) 14.dp else 9.dp)
        ) {
            DetailMoveControl(
                label = if (wide) "Previous" else "Prev",
                icon = HugeIcons.Back,
                onClick = onPrevious
            )
            Text(
                text = "${(index + 1).toString().padStart(2, '0')} / ${componentCatalog.size}",
                color = StudioMuted,
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.sp
            )
            DetailMoveControl(
                label = "Next",
                icon = HugeIcons.Next,
                iconAfter = true,
                onClick = onNext
            )
        }
    }
}

@Composable
private fun DetailMoveControl(
    label: String,
    icon: org.jetbrains.compose.resources.DrawableResource,
    onClick: () -> Unit,
    iconAfter: Boolean = false
) {
    Row(
        modifier = Modifier.clickable(onClick = onClick).padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (!iconAfter) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = StudioInk
            )
        }
        Text(
            text = label,
            color = StudioInk,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
        if (iconAfter) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = StudioInk
            )
        }
    }
}

@Composable
private fun DetailSource(
    component: SharedComponent,
    modifier: Modifier = Modifier
) {
    val palette = paletteFor(component.category)
    Column(
        modifier = modifier.background(StudioInk).padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(Modifier.width(52.dp).height(5.dp).background(palette.accent))
        Text(
            text = component.name,
            color = StudioPaper,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = 38.sp,
            lineHeight = 40.sp
        )
        Text(
            text = component.description,
            color = StudioPaper.copy(alpha = 0.62f),
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 21.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "COMMONMAIN.KT",
                color = palette.accent,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "SOURCE",
                color = StudioPaper.copy(alpha = 0.42f),
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.2.sp
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF090A0C))
                .border(1.dp, Color(0xFF303238), RoundedCornerShape(4.dp))
                .padding(14.dp)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
        ) {
            Text(
                text = component.sourceCode,
                color = Color(0xFFE9EAF0),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun StudioDivider(vertical: Boolean = false) {
    Box(
        modifier = if (vertical) {
            Modifier.width(1.dp).fillMaxHeight().background(StudioLine)
        } else {
            Modifier.fillMaxWidth().height(1.dp).background(StudioLine)
        }
    )
}

@Composable
private fun StudioFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(StudioInk)
            .padding(horizontal = 16.dp, vertical = 22.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "COMPOSABLE",
            color = StudioPaper,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = 25.sp
        )
        Text(
            text = "BUILT TO BE USED",
            color = StudioPaper.copy(alpha = 0.5f),
            style = MaterialTheme.typography.labelSmall,
            letterSpacing = 1.1.sp
        )
    }
}
