import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.subDI
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.core_ui.Platform
import ru.slartus.moca.core_ui.theme.AppResources
import ru.slartus.moca.data.di.dataModule
import ru.slartus.moca.domain.di.domainModule
import ru.slartus.moca.features.`feature-main`.di.mainScreenModule
import ru.slartus.moca.features.`feature-product-info`.di.productInfoModule
import ru.slartus.moca.features.`feature-search`.di.searchScreenModule
import ru.slartus.moca.features.`feature-settings`.di.settingsModule

lateinit var appDi: DI

@Composable
fun withApp(parentId:DI, content: @Composable () -> Unit) {
    val appResources = AppResources(LocalAppStrings.current)
    val coroutineScope = rememberCoroutineScope()
    appDi = subDI(parentId){
        import(dataModule)
        import(domainModule)
        bindSingleton { appResources }
        bindSingleton { coroutineScope }

        import(mainScreenModule)
        import(productInfoModule)
        import(searchScreenModule)
        import(settingsModule)
    }
    withDI(appDi) {
        content()
    }
}

expect fun getPlatform(): Platform
