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
import ru.slartus.moca.domain.models.Tv
import ru.slartus.moca.domain.repositories.SeriesRepository

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PopularTvView(
    modifier: Modifier = Modifier,
    onItemClick: (item: Tv) -> Unit = {},
    onError: (ex: Exception) -> Unit = {}
) {
    var viewState: GridViewState<Tv> by remember {
        mutableStateOf(
            GridViewState(
                isLoading = true,
                data = emptyList(),
                error = null
            )
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        VideoGridView(modifier = Modifier.fillMaxSize(),
            data = viewState.data.map { VideoCard(it.id, it.title, it.posterUrl) })
        { card ->
            onItemClick(viewState.data.single { it.id == card.id })
        }
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

    val repository: SeriesRepository by rememberInstance()
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