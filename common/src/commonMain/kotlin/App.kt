import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.*
import org.kodein.di.compose.withDI
import ru.slartus.moca.core_ui.Platform
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.data.di.dataModule
import ru.slartus.moca.domain.di.domainModule
import ru.slartus.moca.features.feature_main.MainScreen

@Composable
fun App() = withDI(dataModule, domainModule) {
    val platformSettings = PlatformSettings(getPlatform())
    AppTheme(platformSettings = platformSettings, darkTheme = true) {
        MainScreen()
    }
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

