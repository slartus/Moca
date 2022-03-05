import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

object CoreUiIconsResolve {
    @Composable
    fun icMovies() = painterResource("images/ic_movies.svg")
    @Composable
    fun icTv() = painterResource("images/ic_series.svg")
    @Composable
    fun icPlay() = painterResource("images/ic_play_circle_outline_black_24dp.svg")
}