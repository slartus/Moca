import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import ru.slartus.moca.core_ui.Platform

actual fun getPlatform() = Platform.Desktop

actual fun getHttpClient(): HttpClient = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}

@Composable
actual fun AsyncImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale
) {
    AsyncImage(
        load = { loadImageBitmap(imageUrl) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}
