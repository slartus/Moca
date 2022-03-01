import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import ru.slartus.moca.core_ui.Platform

object CoreUiAppResolve {
    fun getPlatform() = Platform.Desktop


    @Composable
    fun AsyncImage(
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

}
