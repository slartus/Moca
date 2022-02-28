import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import io.ktor.client.*
import org.kodein.di.compose.withDI
import ru.slartus.moca.data.di.dataModule
import ru.slartus.moca.domain.di.domainModule
import ru.slartus.moca.features.feature_main.MainScreen

@Composable
fun App() = withDI(dataModule, domainModule) {
    MaterialTheme {
        MainScreen()
    }
}

expect fun getHttpClient(): HttpClient

@Composable
expect fun AsyncImage(imageUrl: String)
