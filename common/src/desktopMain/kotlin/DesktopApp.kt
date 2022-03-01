import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

actual fun getPlatform() = CoreUiAppResolve.getPlatform()

actual fun getHttpClient() = DataAppResolve.getHttpClient()

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
) = CoreUiAppResolve.AsyncImage(modifier, imageUrl, contentDescription, contentScale)
