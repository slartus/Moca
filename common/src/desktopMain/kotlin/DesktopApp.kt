import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import java.io.File

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
actual fun AsyncImage(imageUrl: String) {
    AsyncImage(
        load = { loadImageBitmap(imageUrl) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "Sample",
        modifier = Modifier.width(200.dp)
    )
}
