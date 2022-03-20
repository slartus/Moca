package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.`core-ui`.views.VideoCard
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Series

@Composable
internal fun MoviesView(
    modifier: Modifier = Modifier,
    tag: String,
    refresh: Boolean,
    onItemClick: (item: Movie) -> Unit = {},
    onError: (ex: Exception) -> Unit = {}
) {
    val screenViewModel: ProductsViewModel<Movie> by rememberInstance(tag)
    ProductsView(
        modifier = modifier,
        screenViewModel = screenViewModel,
        refresh = refresh,
        onItemClick = onItemClick,
        onError = onError
    )
}

@Composable
internal fun SeriesView(
    modifier: Modifier = Modifier,
    tag: String,
    refresh: Boolean,
    onItemClick: (item: Series) -> Unit = {},
    onError: (ex: Exception) -> Unit = {}
) {
    val screenViewModel: ProductsViewModel<Series> by rememberInstance(tag)
    ProductsView(
        modifier = modifier,
        screenViewModel = screenViewModel,
        refresh = refresh,
        onItemClick = onItemClick,
        onError = onError
    )
}

@Composable
private fun <T : Product> ProductsView(
    modifier: Modifier = Modifier,
    screenViewModel: ProductsViewModel<T>,
    refresh: Boolean,
    onItemClick: (item: T) -> Unit = {},
    onError: (ex: Exception) -> Unit = {}
) {
    val viewState by screenViewModel.stateFlow.collectAsState()
    val actions by screenViewModel.actionsFlow.collectAsState()
    actions.firstOrNull()?.let {
        screenViewModel.actionReceived(it.id)
        when (it) {
            is Action.Error ->
                onError(it.ex)
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        VideoGridView(
            modifier = Modifier.fillMaxSize(),
            data = viewState.data.map { VideoCard(it.id, it.title, it.posterUrl) },
            onCardClick = { card ->
                onItemClick(viewState.data.single { it.id == card.id })
            },
            onLoadMoreEvent = screenViewModel::loadMore
        )

        if (viewState.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppTheme.colors.highLight,
            )
        else if (viewState.data.isEmpty())
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No data",
                color = AppTheme.colors.secondaryVariant
            )
    }
    if (refresh) {
        screenViewModel.reload()
    }
}