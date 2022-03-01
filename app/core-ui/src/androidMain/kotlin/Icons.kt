import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.slartus.moca.core_ui.R

@Composable
actual fun icMovies() = painterResource(R.drawable.ic_movies)

@Composable
actual fun icTv() = painterResource(R.drawable.ic_series)

object CoreUiIconsResolve {
    @Composable
    fun icMovies() = painterResource(R.drawable.ic_movies)

    @Composable
    fun icTv() = painterResource(R.drawable.ic_series)
}