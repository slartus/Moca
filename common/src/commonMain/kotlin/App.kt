import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.core_ui.Platform
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

expect fun getPlatform(): Platform
