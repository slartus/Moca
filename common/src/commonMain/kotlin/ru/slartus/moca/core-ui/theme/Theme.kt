package ru.slartus.moca.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.therabody.plus.core_lib.ui.compose.themes.AppColors
import com.therabody.plus.core_lib.ui.compose.themes.LocalAppColors
import com.therabody.plus.core_lib.ui.compose.themes.darkPalette
import com.therabody.plus.core_lib.ui.compose.themes.lightPalette
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings

@Composable
fun AppTheme(
    platformSettings: PlatformSettings,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkPalette else lightPalette

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalPlatformSettings provides platformSettings,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}
