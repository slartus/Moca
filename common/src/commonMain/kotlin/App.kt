import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import io.ktor.client.*
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import ru.slartus.moca.data.TmdbApi
import ru.slartus.moca.data.di.httpModule
import ru.slartus.moca.data.models.Genre

@Composable
fun App() = withDI(httpModule) {
    var genres: List<Genre> by mutableStateOf(emptyList())
    MaterialTheme {
        var text by remember { mutableStateOf("Hello, World1!") }

        Button(onClick = {
            text = "Hello, ${getPlatformName()}"
        }) {
            Text(text)
        }

        LazyColumn {
            genres.forEach { genre ->
                item {
                    Text(text = genre.name ?: "no")
                }
            }

        }
    }
    val tmdbApi: TmdbApi by rememberInstance()
    LaunchedEffect(key1 = Unit, block = {
        genres = tmdbApi.Genres().getMovieList()
    })
}

expect fun getHttpClient(): HttpClient
expect fun getPlatformName(): String