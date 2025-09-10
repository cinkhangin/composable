package com.naulian.composable.component

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter


fun LocalDate.toFriendlyDateString(): String {
    val today = LocalDate.now()
    return when {
        this.isEqual(today) -> "Today"
        this.isEqual(today.plusDays(1)) -> "Tomorrow"
        this.isEqual(today.minusDays(1)) -> "Yesterday"
        else -> this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }
}