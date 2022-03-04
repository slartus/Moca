import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import di.AppModule
import io.ktor.client.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.subDI
import ru.slartus.moca.`core-ui`.theme.*
import ru.slartus.moca.core_ui.LocalPlatformSettings
import ru.slartus.moca.core_ui.Platform
import ru.slartus.moca.core_ui.PlatformSettings
import ru.slartus.moca.core_ui.theme.AppResources
import ru.slartus.moca.data.di.dataModule
import ru.slartus.moca.domain.di.domainModule
import ru.slartus.moca.features.`feature-main`.di.mainScreenModule

lateinit var appDi: DI

@Composable
fun withApp(content: @Composable () -> Unit) {
    val appResources = AppResources(LocalAppStrings.current)
    val coroutineScope = rememberCoroutineScope()
    appDi = DI {
        import(dataModule)
        import(domainModule)
        bindSingleton { appResources }
        bindSingleton { coroutineScope }

        import(mainScreenModule)
    }
    withDI(appDi) {
        content()
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
