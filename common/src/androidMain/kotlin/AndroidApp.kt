import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

actual fun getHttpClient(): HttpClient = HttpClient(CIO){
    install(JsonFeature){
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}
@Composable
actual fun AsyncImage(imageUrl:String){
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
}