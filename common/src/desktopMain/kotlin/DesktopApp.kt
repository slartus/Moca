import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

actual fun getPlatform() = CoreUiAppResolve.getPlatform()

actual fun getHttpClient() = DataAppResolve.getHttpClient()

@Composable
actual fun AsyncImage(
    modifier: Modifier,
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale
) = CoreUiAppResolve.AsyncImage(modifier, imageUrl, contentDescription, contentScale)
