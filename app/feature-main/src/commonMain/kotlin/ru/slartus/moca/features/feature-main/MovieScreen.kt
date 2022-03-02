package ru.slartus.moca.features.`feature-main`

import androidx.compose.runtime.Composable
import ru.slartus.moca.core_ui.ScreenWidth
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.features.`feature-main`.views.TopBarView

@Composable
fun MovieScreen(movie: Movie) {
    TopBarView(title = movie.title, screenWidth = ScreenWidth.Small)
}