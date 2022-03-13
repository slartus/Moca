
import androidx.compose.runtime.ProvidedValue
import ru.slartus.moca.`core-ui`.theme.Language
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.theme.enStrings
import ru.slartus.moca.`core-ui`.theme.ruStrings
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.PlatformSettings

fun appProviders(): Array<ProvidedValue<out Any>> {
    val language = Language.Ru

    val platformSettings = PlatformSettings(getPlatform())


    val strings = when (language) {
        Language.Ru -> ruStrings
        Language.En -> enStrings
    }


    return arrayOf(

        LocalAppStrings provides strings,
        LocalPlatformSettings provides platformSettings
    )
}