package ru.slartus.moca.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.slartus.moca.`core-ui`.theme.AppColors
import ru.slartus.moca.`core-ui`.theme.LocalAppColors
import ru.slartus.moca.`core-ui`.theme.darkPalette
import ru.slartus.moca.`core-ui`.theme.lightPalette
import ru.slartus.moca.`core-ui`.theme.AppStrings
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.theme.ruStrings
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings

@Composable
fun AppTheme(
    platformSettings: PlatformSettings,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkPalette else lightPalette
    val strings = ruStrings

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppStrings provides strings,
        LocalPlatformSettings provides platformSettings,
        content = content
    )
}

object AppTheme {
    val strings: AppStrings
        @Composable
        get() = LocalAppStrings.current

    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}
