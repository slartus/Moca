package ru.slartus.moca.core_ui

import androidx.compose.runtime.staticCompositionLocalOf

data class PlatformSettings(val platform: Platform)

enum class Platform {
    Android,
    Desktop
}

val LocalPlatformSettings = staticCompositionLocalOf<PlatformSettings> {
    error("No PlatformSettings provided")
}


