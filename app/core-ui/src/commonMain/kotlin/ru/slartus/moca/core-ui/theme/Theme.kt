package ru.slartus.moca.core_ui.theme

import androidx.compose.runtime.Composable
import ru.slartus.moca.`core-ui`.theme.AppColors
import ru.slartus.moca.`core-ui`.theme.AppStrings
import ru.slartus.moca.`core-ui`.theme.LocalAppColors
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings

object AppTheme {
    val strings: AppStrings
        @Composable
        get() = LocalAppStrings.current

    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}
