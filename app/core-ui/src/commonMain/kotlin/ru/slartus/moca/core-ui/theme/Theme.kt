package ru.slartus.moca.`core-ui`.theme

import androidx.compose.material.ButtonColors
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current


    val buttonColors: ButtonColors
        @Composable
        get() = DefaultButtonColors(
            backgroundColor = colors.highLight,
            contentColor = colors.primaryVariant,
            disabledBackgroundColor = colors.highLight,
            disabledContentColor = colors.secondaryVariant
        )
}

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkPalette else lightPalette
    val textStyle = TextStyle(color = colors.secondaryVariant)

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalTextStyle provides textStyle
    ) {
        MaterialTheme(colors = colors.toMaterialColors()) {
            content()
        }
    }
}

data class AppResources(val strings: AppStrings)

@Immutable
private class DefaultButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color
) : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultButtonColors

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}