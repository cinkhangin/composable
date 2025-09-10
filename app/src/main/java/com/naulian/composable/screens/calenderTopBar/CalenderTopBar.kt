package com.naulian.composable.screens.calenderTopBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.component.toFriendlyDateString
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

@Composable
fun CalenderTopBar(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onDateSelected: (LocalDate) -> Unit = {},
    gradient: Brush = Brush.verticalGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.60f),
            Color.Gray.copy(alpha = 0.40f),
            Color.Gray.copy(alpha = 0.06f),
        )
    ),
) {
    val dataSource = remember { CalendarDataSource() }
    var selectedDate by remember { mutableStateOf(dataSource.today) }

    var calendarUiModel by remember(selectedDate) {
        mutableStateOf(dataSource.getData(lastSelectedDate = selectedDate))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 7.dp, vertical = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(data = calendarUiModel)
            Spacer(modifier = Modifier.height(10.dp))
            WeekPager(
                calendarUiModel = calendarUiModel,
                onDateSelected = { newDate ->
                    if (enabled) {
                        selectedDate = newDate
                        onDateSelected(newDate)
                    }
                },
                onWeekChanged = { newStartDate ->
                    selectedDate = newStartDate
                    onDateSelected(newStartDate)
                    calendarUiModel = dataSource.getData(
                        startDate = newStartDate,
                        lastSelectedDate = newStartDate
                    )
                },
                enabled = enabled
            )
        }
    }
}


@Composable
fun Header(data: CalendarUiModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = data.selectedDate.date.toFriendlyDateString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun WeekPager(
    calendarUiModel: CalendarUiModel,
    enabled: Boolean = true,
    onDateSelected: (LocalDate) -> Unit,
    onWeekChanged: (LocalDate) -> Unit
) {
    val initialWeekStart = remember(calendarUiModel.selectedDate.date) {
        calendarUiModel.selectedDate.date.with(DayOfWeek.MONDAY)
    }

    val pageCount = 10000 // Simulate infinite pages
    val initialIndex = pageCount / 2
    val pagerState = rememberPagerState(initialPage = initialIndex){
        pageCount
    }

    var isInitial by remember { mutableStateOf(true) }


    // Scroll to the initial index when the selected date changes
    LaunchedEffect(initialWeekStart) {
        pagerState.scrollToPage(initialIndex)
    }

    // Trigger week change when the page changes
    LaunchedEffect(pagerState.currentPage) {
        if (isInitial) {
            isInitial = false
            return@LaunchedEffect
        }
        val weekOffset = pagerState.currentPage - initialIndex
        val newWeekStart = initialWeekStart.plusWeeks(weekOffset.toLong())
        onWeekChanged(newWeekStart)
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        userScrollEnabled = enabled
    ) { page ->
        val weekOffset = page - initialIndex
        val weekStartDate = initialWeekStart.plusWeeks(weekOffset.toLong())
        WeekRow(
            startDate = weekStartDate,
            selectedDate = calendarUiModel.selectedDate.date,
            onDateSelected = onDateSelected,
            enabled = enabled
        )
    }
}

@Composable
fun WeekRow(
    startDate: LocalDate,
    selectedDates: List<LocalDate> = emptyList(),
    selectedDate: LocalDate,
    selectedTextColor: Color = Color.White,
    unselectedColor: Color = MaterialTheme.colorScheme.onBackground,
    borderColor: Color = MaterialTheme.colorScheme.onBackground,
    selectedContainerColor: Color = Color.Gray,
    enabled: Boolean = true,
    selectedItemWidth: Dp = 40.dp,
    unselectedItemWidth: Dp = 35.dp,
    horizontalPadding: Dp = 8.dp,
    onDateSelected: (LocalDate) -> Unit
) {
    val weekDays = remember(startDate) {
        (0..6).map { startDate.plusDays(it.toLong()) }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        weekDays.forEach { date ->
            ContentItem(
                date = CalendarUiModel.Date(
                    date = date,
                    isSelected = if (selectedDates.isEmpty()) date == selectedDate else date in selectedDates,
                ),
                onClick = { onDateSelected(date) },
                enabled = enabled,
                selectedTextColor = selectedTextColor,
                unselectedTextColor = unselectedColor,
                borderColor = borderColor,
                selectedContainerColor = selectedContainerColor,
                selectedItemWidth = selectedItemWidth,
                unselectedItemWidth = unselectedItemWidth
            )
        }
    }
}


@Composable
fun ContentItem(
    selectedContainerColor: Color = Color.Cyan,
    date: CalendarUiModel.Date,
    onClick: () -> Unit,
    enabled: Boolean = true,
    unselectedTextColor: Color = Color.LightGray,
    selectedTextColor: Color = Color.White,
    borderColor: Color = Color.LightGray,
    selectedItemWidth: Dp = 40.dp,
    unselectedItemWidth: Dp = 35.dp
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 6.dp)
            .clip(if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(34.dp))
            .let {
                if (date.isSelected.not()) {
                    it.border(
                        width = Dp.Hairline,
                        color = borderColor,
                        shape = if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(
                            34.dp
                        )
                    )
                } else {
                    it
                }
            }
            .then(
                if (enabled) {
                    Modifier.clickable(
                        onClick = onClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple())
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (date.isSelected) {
                selectedContainerColor
            } else
                Color.Transparent
        ),
    ) {
        Column(
            modifier = Modifier
                .width(if (date.isSelected) selectedItemWidth else unselectedItemWidth)
                .height(48.dp)
                .clip(if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(34.dp))
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (date.isSelected) {
                Text(
                    text = date.day, // day "Mon", "Tue"
                    color = selectedTextColor,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Text(
                    text = date.date.dayOfMonth.toString(),
                    color = selectedTextColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 2.dp),
                )
            } else {
                Text(
                    text = date.day, // day "Mon", "Tue"
                    color = unselectedTextColor,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
                Text(
                    text = date.date.dayOfMonth.toString(), // date "15", "16"
                    color = unselectedTextColor,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}


class CalendarDataSource {

    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(7)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )
}


data class CalendarUiModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {

    val startDate: Date = visibleDates.first()
    val endDate: Date = visibleDates.last()

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean,
        val isToday: Boolean = date == LocalDate.now()
    ) {
        val day: String = date.format(DateTimeFormatter.ofPattern("E"))
    }
}


val calenderTopBarCode by lazy {
    """
        @Composable
        fun CalenderTopBar(
            modifier: Modifier = Modifier,
            enabled: Boolean = true,
            onDateSelected: (LocalDate) -> Unit = {},
            gradient: Brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Gray.copy(alpha = 0.60f),
                    Color.Gray.copy(alpha = 0.40f),
                    Color.Gray.copy(alpha = 0.06f),
                )
            ),
        ) {
            val dataSource = remember { CalendarDataSource() }
            var selectedDate by remember { mutableStateOf(dataSource.today) }

            var calendarUiModel by remember(selectedDate) {
                mutableStateOf(dataSource.getData(lastSelectedDate = selectedDate))
            }

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(gradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 7.dp, vertical = 7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Header(data = calendarUiModel)
                    Spacer(modifier = Modifier.height(10.dp))
                    WeekPager(
                        calendarUiModel = calendarUiModel,
                        onDateSelected = { newDate ->
                            if (enabled) {
                                selectedDate = newDate
                                onDateSelected(newDate)
                            }
                        },
                        onWeekChanged = { newStartDate ->
                            selectedDate = newStartDate
                            onDateSelected(newStartDate)
                            calendarUiModel = dataSource.getData(
                                startDate = newStartDate,
                                lastSelectedDate = newStartDate
                            )
                        },
                        enabled = enabled
                    )
                }
            }
        }
        
        
        @Composable
        fun Header(data: CalendarUiModel) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = data.selectedDate.date.toFriendlyDateString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


        @Composable
        private fun WeekPager(
            calendarUiModel: CalendarUiModel,
            enabled: Boolean = true,
            onDateSelected: (LocalDate) -> Unit,
            onWeekChanged: (LocalDate) -> Unit
        ) {
            val initialWeekStart = remember(calendarUiModel.selectedDate.date) {
                calendarUiModel.selectedDate.date.with(DayOfWeek.MONDAY)
            }

            val pageCount = 10000 // Simulate infinite pages
            val initialIndex = pageCount / 2
            val pagerState = rememberPagerState(initialPage = initialIndex){
                pageCount
            }

            var isInitial by remember { mutableStateOf(true) }


            // Scroll to the initial index when the selected date changes
            LaunchedEffect(initialWeekStart) {
                pagerState.scrollToPage(initialIndex)
            }

            // Trigger week change when the page changes
            LaunchedEffect(pagerState.currentPage) {
                if (isInitial) {
                    isInitial = false
                    return@LaunchedEffect
                }
                val weekOffset = pagerState.currentPage - initialIndex
                val newWeekStart = initialWeekStart.plusWeeks(weekOffset.toLong())
                onWeekChanged(newWeekStart)
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = enabled
            ) { page ->
                val weekOffset = page - initialIndex
                val weekStartDate = initialWeekStart.plusWeeks(weekOffset.toLong())
                WeekRow(
                    startDate = weekStartDate,
                    selectedDate = calendarUiModel.selectedDate.date,
                    onDateSelected = onDateSelected,
                    enabled = enabled
                )
            }
        }

        @Composable
        fun WeekRow(
            startDate: LocalDate,
            selectedDates: List<LocalDate> = emptyList(),
            selectedDate: LocalDate,
            selectedTextColor: Color = Color.White,
            unselectedColor: Color = MaterialTheme.colorScheme.onBackground,
            borderColor: Color = MaterialTheme.colorScheme.onBackground,
            selectedContainerColor: Color = Color.Gray,
            enabled: Boolean = true,
            selectedItemWidth: Dp = 40.dp,
            unselectedItemWidth: Dp = 35.dp,
            horizontalPadding: Dp = 8.dp,
            onDateSelected: (LocalDate) -> Unit
        ) {
            val weekDays = remember(startDate) {
                (0..6).map { startDate.plusDays(it.toLong()) }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                weekDays.forEach { date ->
                    ContentItem(
                        date = CalendarUiModel.Date(
                            date = date,
                            isSelected = if (selectedDates.isEmpty()) date == selectedDate else date in selectedDates,
                        ),
                        onClick = { onDateSelected(date) },
                        enabled = enabled,
                        selectedTextColor = selectedTextColor,
                        unselectedTextColor = unselectedColor,
                        borderColor = borderColor,
                        selectedContainerColor = selectedContainerColor,
                        selectedItemWidth = selectedItemWidth,
                        unselectedItemWidth = unselectedItemWidth
                    )
                }
            }
        }


        @Composable
        fun ContentItem(
            selectedContainerColor: Color = Color.Cyan,
            date: CalendarUiModel.Date,
            onClick: () -> Unit,
            enabled: Boolean = true,
            unselectedTextColor: Color = Color.LightGray,
            selectedTextColor: Color = Color.White,
            borderColor: Color = Color.LightGray,
            selectedItemWidth: Dp = 40.dp,
            unselectedItemWidth: Dp = 35.dp
        ) {
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 6.dp)
                    .clip(if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(34.dp))
                    .let {
                        if (date.isSelected.not()) {
                            it.border(
                                width = Dp.Hairline,
                                color = borderColor,
                                shape = if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(
                                    34.dp
                                )
                            )
                        } else {
                            it
                        }
                    }
                    .then(
                        if (enabled) {
                            Modifier.clickable(
                                onClick = onClick,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple())
                        } else {
                            Modifier
                        }
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (date.isSelected) {
                        selectedContainerColor
                    } else
                        Color.Transparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .width(if (date.isSelected) selectedItemWidth else unselectedItemWidth)
                        .height(48.dp)
                        .clip(if (date.isSelected) RoundedCornerShape(24.dp) else RoundedCornerShape(34.dp))
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (date.isSelected) {
                        Text(
                            text = date.day, // day "Mon", "Tue"
                            color = selectedTextColor,
                            fontSize = 10.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )

                        Text(
                            text = date.date.dayOfMonth.toString(),
                            color = selectedTextColor,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 2.dp),
                        )
                    } else {
                        Text(
                            text = date.day, // day "Mon", "Tue"
                            color = unselectedTextColor,
                            fontSize = 10.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                        Text(
                            text = date.date.dayOfMonth.toString(), // date "15", "16"
                            color = unselectedTextColor,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                    }
                }
            }
        }


        class CalendarDataSource {

            val today: LocalDate
                get() {
                    return LocalDate.now()
                }

            fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
                val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
                val endDayOfWeek = firstDayOfWeek.plusDays(7)
                val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
                return toUiModel(visibleDates, lastSelectedDate)
            }

            private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
                val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
                return Stream.iterate(startDate) { date ->
                    date.plusDays(/* daysToAdd = */ 1)
                }
                    .limit(numOfDays)
                    .collect(Collectors.toList())
            }

            private fun toUiModel(
                dateList: List<LocalDate>,
                lastSelectedDate: LocalDate
            ): CalendarUiModel {
                return CalendarUiModel(
                    selectedDate = toItemUiModel(lastSelectedDate, true),
                    visibleDates = dateList.map {
                        toItemUiModel(it, it.isEqual(lastSelectedDate))
                    },
                )
            }

            private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
                isSelected = isSelectedDate,
                isToday = date.isEqual(today),
                date = date,
            )
        }


        data class CalendarUiModel(
            val selectedDate: Date,
            val visibleDates: List<Date>
        ) {

            val startDate: Date = visibleDates.first()
            val endDate: Date = visibleDates.last()

            data class Date(
                val date: LocalDate,
                val isSelected: Boolean,
                val isToday: Boolean = date == LocalDate.now()
            ) {
                val day: String = date.format(DateTimeFormatter.ofPattern("E"))
            }
        }
    """.trimIndent()
}
