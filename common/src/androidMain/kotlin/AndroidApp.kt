import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

actual fun getPlatform() = AppResolve.getPlatform()

actual fun getHttpClient(): HttpClient = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

@Composable
actual fun AsyncImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale
) = AppResolve.AsyncImage(modifier, imageUrl, contentDescription, contentScale)