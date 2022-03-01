package ru.slartus.moca.features.feature_popular

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.views.VideoCard
import ru.slartus.moca.`core-ui`.views.VideoCardView
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.MoviesRepository
import ru.slartus.moca.features.`feature-main`.views.GridViewState
import ru.slartus.moca.features.`feature-main`.views.VideoGridView

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PopularMoviesView(
    onError: (ex: Exception) -> Unit = {}
) {
    var viewState: GridViewState<Movie> by remember {
        mutableStateOf(
            GridViewState(
                isLoading = true,
                data = emptyList(),
                error = null
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        VideoGridView(modifier = Modifier.fillMaxSize(),
            data = viewState.data.map { VideoCard(it.title, it.posterUrl) })
        
        if (viewState.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppTheme.colors.primary,
            )
        else if (viewState.data.isEmpty())
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No data",
                color = AppTheme.colors.primaryText
            )
        viewState.error?.let {
            onError(it)
        }
    }

    val repository: MoviesRepository by rememberInstance()
    LaunchedEffect(key1 = Unit, block = {
        viewState = try {
            val popular = repository.getPopular()
            GridViewState(
                isLoading = false,
                data = popular,
                error = viewState.error
            )
        } catch (ex: Exception) {
            GridViewState(
                isLoading = false,
                data = viewState.data,
                error = ex
            )
        }
    })
}