package ru.slartus.moca.core_ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ScreenWidth {
    Small, Medium, Large
}

val Dp.screenWidth: ScreenWidth
    get() = when {
        this < 680.dp -> ScreenWidth.Small
        this < 1280.dp -> ScreenWidth.Medium
        else -> ScreenWidth.Large
    }
