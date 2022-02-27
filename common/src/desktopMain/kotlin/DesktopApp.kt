import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import io.ktor.client.*

actual fun getPlatformName(): String = "Desktop"
actual fun getHttpClient(): HttpClient = HttpClient()

@Preview
@Composable
fun AppPreview() {
    App()
}