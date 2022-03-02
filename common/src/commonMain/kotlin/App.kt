import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.*
import org.kodein.di.compose.withDI
import ru.slartus.moca.`core-ui`.theme.*
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.Platform
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.data.di.dataModule
import ru.slartus.moca.domain.di.domainModule

fun appProviders(): Array<ProvidedValue<out Any>> {
    val darkTheme = true
    val language = Language.Ru

    val platformSettings = PlatformSettings(getPlatform())

    val colors = if (darkTheme) darkPalette else lightPalette
    val strings = when (language) {
        Language.Ru -> ruStrings
        Language.En -> enStrings
    }
    return arrayOf(
        LocalAppColors provides colors,
        LocalAppStrings provides strings,
        LocalPlatformSettings provides platformSettings
    )
}

@Composable
fun withDI(content: @Composable () -> Unit): Unit =
    withDI(dataModule, domainModule, ) {
        content()
    }


expect fun getHttpClient(): HttpClient

@Composable
expect fun AsyncImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale
)

expect fun getPlatform(): Platform
