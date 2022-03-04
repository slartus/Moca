package di

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.slartus.moca.core_ui.theme.AppResources
import ru.slartus.moca.features.`feature-main`.MainScreenViewModel

fun AppModule(
    appResources: AppResources,
    scope: CoroutineScope
) = DI.Module("appModule") {
    //bindSingleton { MainScreenViewModel(appResources, scope) }
}