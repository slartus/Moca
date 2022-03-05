package ru.slartus.moca.features.`feature-main`.videoGridViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.kodein.di.compose.rememberInstance
import ru.slartus.moca.`core-ui`.views.VideoCard
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PopularMoviesView(
    modifier: Modifier = Modifier,
    tag:String,
    refresh: Boolean,
    onItemClick: (id: Movie) -> Unit = {},
    onError: (ex: Exception) -> Unit = {}
) {

    val screenViewModel: PopularMoviesViewModel by rememberInstance(tag)
    val viewState by screenViewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        VideoGridView(modifier = Modifier.fillMaxSize(),
            data = viewState.data.map { VideoCard(it.id, it.title, it.posterUrl) },
            onCardClick = { card ->
                onItemClick(viewState.data.single { it.id == card.id })
            },
            onLoadMoreEvent = screenViewModel::loadMore
        )

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
    }
    if (refresh) {
        screenViewModel.reload()
    }
}