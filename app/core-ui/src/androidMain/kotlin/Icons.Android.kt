import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.slartus.moca.core_ui.R


object CoreUiIconsResolve {
    @Composable
    fun icMovies() = painterResource(R.drawable.ic_movies)

    @Composable
    fun icTv() = painterResource(R.drawable.ic_series)

    @Composable
    fun icPlay() = painterResource(R.drawable.ic_play_circle_outline_black_24dp)
}