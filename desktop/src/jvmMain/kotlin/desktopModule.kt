import org.kodein.di.DI
import org.kodein.di.bindSingleton

val desktopModule = DI.Module("desktopModule") {
    bindSingleton { SqlDelightDriverFactory() }
}