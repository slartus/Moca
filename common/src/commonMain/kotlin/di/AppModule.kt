package di

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import ru.slartus.moca.`core-ui`.theme.AppResources

fun AppModule(
    appResources: AppResources,
    scope: CoroutineScope
) = DI.Module("appModule") {
    //bindSingleton { MainScreenViewModel(appResources, scope) }
}