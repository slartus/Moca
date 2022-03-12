import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.text.TextStyle
import ru.slartus.moca.`core-ui`.theme.*
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme

fun appProviders(): Array<ProvidedValue<out Any>> {
    val darkTheme = true
    val language = Language.Ru

    val platformSettings = PlatformSettings(getPlatform())

    val colors = if (darkTheme) darkPalette else lightPalette
    val strings = when (language) {
        Language.Ru -> ruStrings
        Language.En -> enStrings
    }
    val textStyle = TextStyle(color = colors.primaryText)
    return arrayOf(
        LocalAppColors provides colors,
        LocalAppStrings provides strings,
        LocalPlatformSettings provides platformSettings,
        LocalTextStyle provides textStyle
    )
}