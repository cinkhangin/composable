package com.ckgin.serene

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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ckgin.modify.HugeIcons
import org.jetbrains.compose.resources.painterResource

private val Ink = Color(0xFF191822)
private val MutedInk = Color(0xFF686672)
private val Canvas = Color(0xFFFBF8F2)
private val Paper = Color(0xFFFFFDF9)
private val Line = Color(0xFFE3DED3)
private val Violet = Color(0xFF6558E8)
private val SoftViolet = Color(0xFFE9E4FF)
private val Acid = Color(0xFFDFFF72)
private val Coral = Color(0xFFFF6B62)
private val CodeInk = Color(0xFF121118)

private enum class ComponentFamily(
    val title: String,
    val shortTitle: String,
    val accent: Color,
    val tint: Color
) {
    Motion("Motion & Canvas", "Motion", Violet, SoftViolet),
    Interactive("Interactive", "Interactive", Coral, Color(0xFFFFE5DF)),
    Surfaces("Surfaces & Shapes", "Surfaces", Color(0xFF168B70), Color(0xFFDFF5ED))
}

private enum class ExampleTab(val label: String) {
    Preview("Preview"),
    Source("Source")
}

private fun SharedComponent.family(): ComponentFamily = when {
    category.contains("Interactive") -> ComponentFamily.Interactive
    category.contains("Static") -> ComponentFamily.Surfaces
    else -> ComponentFamily.Motion
}

private fun SharedComponent.importName(): String = name
    .split(" ")
    .joinToString("") { word -> word.replaceFirstChar { it.uppercase() } }

@Composable
fun App() {
    SereneTheme(darkTheme = false) {
        var selected by remember { mutableStateOf(componentCatalog.first()) }
        var activeTab by remember { mutableStateOf(ExampleTab.Preview) }

        Surface(modifier = Modifier.fillMaxSize(), color = Canvas) {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val desktop = maxWidth >= 980.dp
                if (desktop) {
                    DesktopLibrary(
                        selected = selected,
                        activeTab = activeTab,
                        onSelect = {
                            selected = it
                            activeTab = ExampleTab.Preview
                        },
                        onTabChange = { activeTab = it }
                    )
                } else {
                    MobileLibrary(
                        selected = selected,
                        activeTab = activeTab,
                        onSelect = {
                            selected = it
                            activeTab = ExampleTab.Preview
                        },
                        onTabChange = { activeTab = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun DesktopLibrary(
    selected: SharedComponent,
    activeTab: ExampleTab,
    onSelect: (SharedComponent) -> Unit,
    onTabChange: (ExampleTab) -> Unit
) {
    Column(Modifier.fillMaxSize().background(Canvas)) {
        LibraryHeader(compact = false)
        Hairline()
        Row(Modifier.fillMaxWidth().weight(1f)) {
            LibrarySidebar(
                selected = selected,
                onSelect = onSelect,
                modifier = Modifier.width(272.dp).fillMaxHeight()
            )
            Hairline(vertical = true)
            DocumentationPage(
                component = selected,
                activeTab = activeTab,
                onTabChange = onTabChange,
                compact = false,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            Hairline(vertical = true)
            PageIndex(
                component = selected,
                modifier = Modifier.width(188.dp).fillMaxHeight()
            )
        }
    }
}

@Composable
private fun MobileLibrary(
    selected: SharedComponent,
    activeTab: ExampleTab,
    onSelect: (SharedComponent) -> Unit,
    onTabChange: (ExampleTab) -> Unit
) {
    Column(Modifier.fillMaxSize().background(Canvas)) {
        LibraryHeader(compact = true)
        Hairline()
        ComponentStrip(
            selected = selected,
            onSelect = onSelect,
            modifier = Modifier.fillMaxWidth()
        )
        Hairline()
        DocumentationPage(
            component = selected,
            activeTab = activeTab,
            onTabChange = onTabChange,
            compact = true,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun LibraryHeader(compact: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) 62.dp else 70.dp)
            .background(Paper)
            .padding(horizontal = if (compact) 16.dp else 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Ink),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Acid)
                        .drawBehind {
                            drawCircle(Coral, radius = 4.dp.toPx(), center = Offset(size.width, 0f))
                        }
                )
            }
            Text(
                text = "serene",
                color = Ink,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-0.4).sp
            )
            Box(
                Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(SoftViolet)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "v0.6",
                    color = Violet,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.4.sp
                )
            }
        }

        if (!compact) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                HeaderLink("Docs", selected = true)
                HeaderLink("Components")
                HeaderLink("Showcase")
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Ink)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "GitHub",
                        color = Paper,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "↗",
                        color = Acid,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Line, RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 7.dp)
            ) {
                Text(
                    "DOCS",
                    color = Ink,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )
            }
        }
    }
}

@Composable
private fun HeaderLink(label: String, selected: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Ink else MutedInk,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
        Box(
            Modifier
                .width(18.dp)
                .height(2.dp)
                .background(if (selected) Violet else Color.Transparent)
        )
    }
}

@Composable
private fun LibrarySidebar(
    selected: SharedComponent,
    onSelect: (SharedComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(Paper),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            SidebarSearch()
        }
        item {
            SidebarSection(
                title = "GETTING STARTED",
                entries = listOf("Introduction", "Installation", "Platforms"),
                selectedEntry = ""
            )
        }
        ComponentFamily.entries.forEach { family ->
            item {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            Modifier
                                .size(7.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(family.accent)
                        )
                        Text(
                            text = family.title.uppercase(),
                            color = MutedInk,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.2.sp
                        )
                    }
                    componentCatalog.filter { it.family() == family }.forEach { component ->
                        SidebarItem(
                            component = component,
                            selected = component.id == selected.id,
                            onClick = { onSelect(component) }
                        )
                    }
                }
            }
        }
        item {
            SidebarNote()
        }
    }
}

@Composable
private fun SidebarSearch() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Canvas)
            .border(1.dp, Line, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Find a component",
            color = MutedInk,
            style = MaterialTheme.typography.bodySmall
        )
        Box(
            Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(Paper)
                .border(1.dp, Line, RoundedCornerShape(5.dp))
                .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Text("⌘ K", color = MutedInk, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SidebarSection(
    title: String,
    entries: List<String>,
    selectedEntry: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
            color = MutedInk,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
        entries.forEach { entry ->
            Text(
                text = entry,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(9.dp))
                    .background(if (selectedEntry == entry) SoftViolet else Color.Transparent)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = if (selectedEntry == entry) Violet else Ink,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (selectedEntry == entry) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun SidebarItem(
    component: SharedComponent,
    selected: Boolean,
    onClick: () -> Unit
) {
    val family = component.family()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) family.tint else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 11.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Box(
                Modifier
                    .width(if (selected) 3.dp else 2.dp)
                    .height(17.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (selected) family.accent else Line)
            )
            Text(
                text = component.name,
                color = Ink,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (selected) {
            Icon(
                painter = painterResource(HugeIcons.Next),
                contentDescription = null,
                modifier = Modifier.size(13.dp),
                tint = family.accent
            )
        }
    }
}

@Composable
private fun SidebarNote() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Ink)
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(Modifier.size(7.dp).clip(RoundedCornerShape(3.dp)).background(Acid))
            Text(
                "MULTIPLATFORM",
                color = Acid,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.1.sp
            )
        }
        Text(
            "One component API for Android, iOS, desktop, and web.",
            color = Paper,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun ComponentStrip(
    selected: SharedComponent,
    onSelect: (SharedComponent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.background(Paper),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 11.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(componentCatalog, key = { it.id }) { component ->
            val active = component.id == selected.id
            val family = component.family()
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(99.dp))
                    .background(if (active) Ink else Canvas)
                    .border(1.dp, if (active) Ink else Line, RoundedCornerShape(99.dp))
                    .clickable { onSelect(component) }
                    .padding(horizontal = 11.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Box(
                    Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (active) Acid else family.accent)
                )
                Text(
                    text = component.name,
                    color = if (active) Paper else Ink,
                    fontSize = 11.sp,
                    fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun DocumentationPage(
    component: SharedComponent,
    activeTab: ExampleTab,
    onTabChange: (ExampleTab) -> Unit,
    compact: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(Canvas),
        contentPadding = PaddingValues(
            start = if (compact) 16.dp else 44.dp,
            end = if (compact) 16.dp else 44.dp,
            top = if (compact) 24.dp else 36.dp,
            bottom = 80.dp
        ),
        verticalArrangement = Arrangement.spacedBy(if (compact) 24.dp else 30.dp)
    ) {
        item {
            ComponentHero(component = component, compact = compact)
        }
        item {
            InstallCommand(compact = compact)
        }
        item {
            ExamplePanel(
                component = component,
                activeTab = activeTab,
                onTabChange = onTabChange,
                compact = compact
            )
        }
        item {
            UsageSection(component = component, compact = compact)
        }
        item {
            ApiSection(component = component, compact = compact)
        }
        item {
            LibraryFooter()
        }
    }
}

@Composable
private fun ComponentHero(component: SharedComponent, compact: Boolean) {
    val family = component.family()
    Column(verticalArrangement = Arrangement.spacedBy(if (compact) 14.dp else 18.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Components",
                color = MutedInk,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text("/", color = Line, fontSize = 12.sp)
            Text(
                family.shortTitle,
                color = family.accent,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                HeroTitle(component.name, compact = true)
                FamilyBadge(family)
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                HeroTitle(component.name, compact = false)
                FamilyBadge(family)
            }
        }
        Text(
            text = component.description,
            modifier = Modifier.widthIn(max = 680.dp),
            color = MutedInk,
            fontSize = if (compact) 14.sp else 16.sp,
            lineHeight = if (compact) 22.sp else 25.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MetaLabel("COMMONMAIN")
            MetaDot(family.accent)
            MetaLabel("ACCESSIBLE")
            MetaDot(Coral)
            MetaLabel("LIVE PREVIEW")
        }
    }
}

@Composable
private fun HeroTitle(title: String, compact: Boolean) {
    Text(
        text = title,
        color = Ink,
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        fontSize = if (compact) 42.sp else 58.sp,
        lineHeight = if (compact) 44.sp else 60.sp,
        letterSpacing = (-1.4).sp
    )
}

@Composable
private fun FamilyBadge(family: ComponentFamily) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(99.dp))
            .background(family.tint)
            .padding(horizontal = 11.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Box(Modifier.size(7.dp).clip(RoundedCornerShape(3.dp)).background(family.accent))
        Text(
            text = family.title,
            color = Ink,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.4.sp
        )
    }
}

@Composable
private fun MetaLabel(label: String) {
    Text(
        text = label,
        color = MutedInk,
        fontSize = 9.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 1.sp
    )
}

@Composable
private fun MetaDot(color: Color) {
    Box(Modifier.size(4.dp).clip(RoundedCornerShape(2.dp)).background(color))
}

@Composable
private fun InstallCommand(compact: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Ink)
            .padding(horizontal = if (compact) 14.dp else 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Text(
                "$",
                color = Acid,
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                if (compact) "com.ckgin:serene-ui:0.1.0"
                else "implementation(\"com.ckgin:serene-ui:0.1.0\")",
                color = Paper,
                fontFamily = FontFamily.Monospace,
                fontSize = if (compact) 10.sp else 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.09f))
                .padding(horizontal = 9.dp, vertical = 6.dp)
        ) {
            Text(
                "GRADLE",
                color = Paper.copy(alpha = 0.75f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.9.sp
            )
        }
    }
}

@Composable
private fun ExamplePanel(
    component: SharedComponent,
    activeTab: ExampleTab,
    onTabChange: (ExampleTab) -> Unit,
    compact: Boolean
) {
    val family = component.family()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Paper)
            .border(1.dp, Line, RoundedCornerShape(18.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ExampleTab.entries.forEach { tab ->
                    val selected = tab == activeTab
                    Text(
                        text = tab.label,
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(if (selected) family.tint else Color.Transparent)
                            .clickable { onTabChange(tab) }
                            .padding(horizontal = 13.dp, vertical = 8.dp),
                        color = if (selected) Ink else MutedInk,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier.padding(end = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Box(Modifier.size(6.dp).clip(RoundedCornerShape(3.dp)).background(family.accent))
                Text(
                    "LIVE",
                    color = MutedInk,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }
        Hairline()
        AnimatedContent(
            targetState = activeTab,
            transitionSpec = { fadeIn(tween(180)) togetherWith fadeOut(tween(120)) },
            label = "example-tab"
        ) { tab ->
            if (tab == ExampleTab.Preview) {
                ComponentPreviewStage(component = component, compact = compact)
            } else {
                SourcePane(component = component, compact = compact)
            }
        }
    }
}

@Composable
private fun ComponentPreviewStage(component: SharedComponent, compact: Boolean) {
    val family = component.family()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) component.previewHeight.coerceIn(310.dp, 430.dp) else 480.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(family.tint, Canvas, Paper),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
            .drawBehind {
                val grid = family.accent.copy(alpha = 0.10f)
                val step = 32.dp.toPx()
                var x = 0f
                while (x <= size.width) {
                    drawLine(grid, Offset(x, 0f), Offset(x, size.height), 1f)
                    x += step
                }
                var y = 0f
                while (y <= size.height) {
                    drawLine(grid, Offset(0f, y), Offset(size.width, y), 1f)
                    y += step
                }
                drawCircle(
                    color = Acid.copy(alpha = 0.72f),
                    radius = if (compact) 54.dp.toPx() else 78.dp.toPx(),
                    center = Offset(size.width * 0.84f, size.height * 0.18f)
                )
                drawCircle(
                    color = Coral.copy(alpha = 0.16f),
                    radius = if (compact) 80.dp.toPx() else 120.dp.toPx(),
                    center = Offset(size.width * 0.10f, size.height * 0.92f),
                    style = Stroke(width = 18.dp.toPx())
                )
            }
            .padding(if (compact) 22.dp else 42.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(if (compact) 18.dp else 24.dp))
                .background(Paper.copy(alpha = 0.92f))
                .border(1.dp, Color.White.copy(alpha = 0.88f), RoundedCornerShape(if (compact) 18.dp else 24.dp))
                .padding(if (compact) 18.dp else 32.dp),
            contentAlignment = Alignment.Center
        ) {
            val demo = component.demo
            if (demo != null) {
                demo()
            } else {
                component.preview(Modifier.fillMaxSize())
            }
        }
        Text(
            text = "${(componentCatalog.indexOf(component) + 1).toString().padStart(2, '0')} / ${componentCatalog.size}",
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(RoundedCornerShape(bottomEnd = 10.dp))
                .background(Ink)
                .padding(horizontal = 10.dp, vertical = 7.dp),
            color = Acid,
            fontFamily = FontFamily.Monospace,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SourcePane(component: SharedComponent, compact: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) 430.dp else 480.dp)
            .background(CodeInk)
            .padding(if (compact) 14.dp else 22.dp)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(
                text = component.sourceCode.lines().indices.joinToString("\n") { (it + 1).toString().padStart(2, '0') },
                color = Paper.copy(alpha = 0.28f),
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                lineHeight = 18.sp
            )
            Text(
                text = component.sourceCode,
                color = Color(0xFFE8E5F2),
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun UsageSection(component: SharedComponent, compact: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionHeading(
            eyebrow = "02 / USAGE",
            title = "Drop it into your composition.",
            compact = compact
        )
        Text(
            text = "Components expose regular Compose parameters and work from shared code. Import the component, then customize it with familiar modifiers and state.",
            modifier = Modifier.widthIn(max = 720.dp),
            color = MutedInk,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 23.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(CodeInk)
                .padding(if (compact) 15.dp else 20.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Text(
                text = """
                    import com.ckgin.serene.${component.importName()}

                    ${component.importName()}(
                        modifier = Modifier.fillMaxWidth()
                    )
                """.trimIndent(),
                color = Color(0xFFEAE7F6),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ApiSection(component: SharedComponent, compact: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionHeading(
            eyebrow = "03 / API",
            title = "A small, Compose-native surface.",
            compact = compact
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Paper)
                .border(1.dp, Line, RoundedCornerShape(14.dp))
        ) {
            ApiRow("modifier", "Modifier", "Modifier", "Layout, drawing, and interaction behavior.", compact)
            Hairline()
            ApiRow("content", "@Composable", "Component default", "The visual content rendered by ${component.name}.", compact)
            Hairline()
            ApiRow("enabled", "Boolean", "true", "Controls whether the component responds to input.", compact)
        }
    }
}

@Composable
private fun SectionHeading(eyebrow: String, title: String, compact: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            eyebrow,
            color = Violet,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
        Text(
            text = title,
            color = Ink,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = if (compact) 28.sp else 34.sp,
            lineHeight = if (compact) 31.sp else 37.sp
        )
    }
}

@Composable
private fun ApiRow(
    name: String,
    type: String,
    default: String,
    description: String,
    compact: Boolean
) {
    if (compact) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ApiCode(name, Violet)
                ApiCode(type, MutedInk)
            }
            Text(description, color = MutedInk, style = MaterialTheme.typography.bodySmall, lineHeight = 19.sp)
            Text("Default: $default", color = Ink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 15.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(Modifier.weight(0.18f)) { ApiCode(name, Violet) }
            Box(Modifier.weight(0.18f)) { ApiCode(type, MutedInk) }
            Box(Modifier.weight(0.18f)) { ApiCode(default, Ink) }
            Text(
                text = description,
                modifier = Modifier.weight(0.46f),
                color = MutedInk,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ApiCode(text: String, color: Color) {
    Text(
        text = text,
        color = color,
        fontFamily = FontFamily.Monospace,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun PageIndex(component: SharedComponent, modifier: Modifier = Modifier) {
    val family = component.family()
    Column(
        modifier = modifier.background(Paper).padding(horizontal = 20.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "ON THIS PAGE",
                color = MutedInk,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp
            )
            PageIndexLink("Preview", selected = true, accent = family.accent)
            PageIndexLink("Usage", accent = family.accent)
            PageIndexLink("API reference", accent = family.accent)
            PageIndexLink("Accessibility", accent = family.accent)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(13.dp))
                .background(family.tint)
                .padding(13.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "LIBRARY STATUS",
                color = family.accent,
                fontSize = 8.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
            Text(
                "${componentCatalog.size} components",
                color = Ink,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                "4 platforms · MIT",
                color = MutedInk,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun PageIndexLink(label: String, selected: Boolean = false, accent: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Box(
            Modifier
                .width(if (selected) 3.dp else 1.dp)
                .height(16.dp)
                .background(if (selected) accent else Line)
        )
        Text(
            label,
            color = if (selected) Ink else MutedInk,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun LibraryFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(Ink, Color(0xFF27233E), Violet)
                )
            )
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Compose once. Delight everywhere.",
                color = Paper,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
            Text(
                "Android · iOS · Desktop · Web",
                color = Paper.copy(alpha = 0.58f),
                fontSize = 9.sp,
                letterSpacing = 0.8.sp
            )
        }
        Box(Modifier.size(11.dp).clip(RoundedCornerShape(4.dp)).background(Acid))
    }
}

@Composable
private fun Hairline(vertical: Boolean = false) {
    Box(
        if (vertical) {
            Modifier.width(1.dp).fillMaxHeight().background(Line)
        } else {
            Modifier.fillMaxWidth().height(1.dp).background(Line)
        }
    )
}
