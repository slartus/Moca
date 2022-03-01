import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import ru.slartus.moca.core_ui.Platform


object AppResolve {
    fun getPlatform() = Platform.Android

    @Composable
    fun AsyncImage(
        modifier: Modifier,
        imageUrl: String,
        contentDescription: String,
        contentScale: ContentScale
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}