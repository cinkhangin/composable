package com.naulian.composable.core.util

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock

fun LocalDate.toFriendlyDateString(): String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return when (this) {
        today -> "Today"
        today + DatePeriod(days = 1) -> "Tomorrow"
        today - DatePeriod(days = 1) -> "Yesterday"
        else -> "${month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }} ${dayOfMonth.toString().padStart(2, '0')}, $year"
    }
}
