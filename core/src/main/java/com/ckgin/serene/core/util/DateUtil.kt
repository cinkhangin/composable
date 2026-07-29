package com.ckgin.serene.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun LocalDate.toFriendlyDateString(): String {
    val today = LocalDate.now()
    return when {
        this == today -> "Today"
        this == today.plusDays(1) -> "Tomorrow"
        this == today.minusDays(1) -> "Yesterday"
        else -> this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }
}